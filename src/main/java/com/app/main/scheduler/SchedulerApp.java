package com.app.main.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.main.altibase.service.KTrainInfoService;
import com.app.main.altibase.service.TimeTableService;
import com.app.main.altibase.vo.KTrainInfoVo;
import com.app.main.altibase.vo.TimeTableMappingVo;
import com.app.main.altibase.vo.TimeTableVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerApp {

	@Autowired
	private KTrainInfoService kTrainInfoService;

	@Autowired
	private TimeTableService timeTableService;

	@Scheduled(cron = "0 0 5 * * *") 
	public void scheduled05() throws Exception{
		log.info("scheduled05()START");
		deleteData();
		callAPI("1");
		callAPI("4");
	}
	
	@Scheduled(cron = "0 0 17 * * *") 
	public void scheduled17() throws Exception{
		log.info("scheduled17()START");
		callAPI("1");
		callAPI("4");
	}
	
	@Scheduled(cron = "0 0 6 * * *")
	public void scheduledTimeTable() throws Exception{
		log.info("scheduledTimeTable()START");
		callAPIScheduledTimeTable();
	}
	
	public void deleteData() throws Exception{
		kTrainInfoService.deleteKTrainInfo();
	}
	
	public void callAPI(String targetLine) throws Exception {
		/**
		 * 
		 * K_TRAIN_INFO
		 * -------------------------------------
		 * TRAIN_Y 열차번호
		 * TRAIN_P 편성번호
		 * LINE	호선
		 * ORG_STN_CD	시작 역코드(서울시)
		 * ORG_STN_NM	시작 역이름(서울시)
		 * DST_STN_CD	종점 역코드(서울시)
		 * DST_STN_NM	종점 역이름(서울시)
		 * -------------------------------------
		 * 
		 * frmtnNo	편성번호       
		 * trainNo 열차번호       
		 * startStation	출발 역코드
		 * endStation	종착 역코드  
		 * 
		 */
		log.info("callAPI("+targetLine+") START AT[ {} ]", new Date());
		List<KTrainInfoVo> kTrainInfoList = new ArrayList<KTrainInfoVo>();
		
        String url = "http://wminwon.korail.com:20310/serverApi/seoulmetro/api.do?auth_code=Gnrqt5aPM2/yWj08lcicuzBKzSTNADoNx0xDdBVygUePIXUJopuYw/sBh4BPyK4w&line="+targetLine;
        
        JSONObject resultObject = new JSONObject(); // OUTPUT DATA 변수
        try{
            URL apiurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)apiurl.openConnection();
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("APIKey", "");  // API 보안키
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {	// 200
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("");
                }
                br.close();
            }else {
                sb.append("{\"CODE\" : \""+conn.getResponseCode()+"\"");
                sb.append(", \"REASON\" : \""+conn.getResponseMessage()+"\"}");
            }

            JSONParser jsonParser  = new JSONParser();
            resultObject = (JSONObject)jsonParser.parse(sb.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        
        JSONArray jsonArr = new JSONArray();
        
        if(resultObject != null) {
        	boolean isValid = (boolean) resultObject.get("isValid");
        	if(isValid) {
        		jsonArr = (JSONArray) resultObject.get("ttcVOList");
        		
        		
        		for(int i=0; i<jsonArr.size(); i++) {
        			JSONObject jo = new JSONObject();
        			
        			KTrainInfoVo kTrainInfoVo = new KTrainInfoVo();
        			
        			jo = (JSONObject) jsonArr.get(i);
        			String frmtnNo = (String) jo.get("frmtnNo"); //frmtnNo	편성번호
        			String trainNo = (String) jo.get("trainNo"); //trainNo 열차번호         
        			String startStation = (String) jo.get("startStation"); //startStation	출발 역코드  
        			String endStation = (String) jo.get("endStation"); //endStation	종착 역코드  
        			
        			
        			kTrainInfoVo.setTrainP(frmtnNo);
        			kTrainInfoVo.setTrainY(trainNo);
        			kTrainInfoVo.setLine(targetLine);
        			kTrainInfoVo.setOrgStnCd(startStation);
        			kTrainInfoVo.setDstStnCd(endStation);
        			
        			kTrainInfoList.add(kTrainInfoVo);
        		}
        	}

        	kTrainInfoService.insertKTrainInfo(kTrainInfoList);
            
        }
        
        log.info("callAPI("+targetLine+") END AT[ {} ]", new Date());
	}
	
	
	public void callAPIScheduledTimeTable() throws Exception {
		
		log.info("callAPIScheduledTimeTable() START AT[ {} ]", new Date());
		//String strUrl = "http://e2ef0752-f9e0-4716-8012-87a5d0f1aaf2.mock.pstmn.io/kr/getTuiTimeTableResult.do";
		String strUrl = "http://www.seoulmetro.co.kr/kr/getTuiTimeTableResult.do";
				
		try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
            con.addRequestProperty("x-api-key", "TEST"); //key값 설정

            con.setRequestMethod("GET");

            //URLConnection에 대한 doOutput 필드값을 지정된 값으로 설정한다. URL 연결은 입출력에 사용될 수 있다. URL 연결을 출력용으로 사용하려는 경우 DoOutput 플래그를 true로 설정하고, 그렇지 않은 경우는 false로 설정해야 한다. 기본값은 false이다.
            con.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음. 
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                
                String response = sb.toString();
                
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Map<String, Object>> listTempMapperFrom = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>() { });
                    
                    List<TimeTableMappingVo> timeTableList = new ArrayList<TimeTableMappingVo>();
                    
                    for(Map<String, Object> tempMapperFrom : listTempMapperFrom) {
                    	
//                    	TimeTableVo t = mapper.convertValue(tempMapperFrom, TimeTableVo.class);
                    	TimeTableMappingVo tm = new TimeTableMappingVo();
                    	
                    	String tmpLine = (String) tempMapperFrom.get("line");
                    	
                    	if(tmpLine != null) {
                    		switch(tmpLine) {
                    			case "01호선": tm.setLine("1");	break;
                    			case "02호선": tm.setLine("2");	break;
                    			case "03호선": tm.setLine("3");	break;
                    			case "04호선": tm.setLine("4");	break;
                    			case "05호선": tm.setLine("5");	break;
                    			case "06호선": tm.setLine("6");	break;
                    			case "07호선": tm.setLine("7");	break;
                    			case "08호선": tm.setLine("8");	break;
                    			case "09호선": tm.setLine("9");	break;
                    			case "경춘선": tm.setLine("G");	break;
                    			case "경의선": tm.setLine("K");	break;
                    			case "경강선": tm.setLine("KK");	break;
                    			case "수인분당선": tm.setLine("SU");	break;
                    			case "신림선": tm.setLine("SL");	break;
                    		}
                    	}
                    	
                		tm.setTrnno((String) tempMapperFrom.get("trainNo"));
                		tm.setDayCd((String) tempMapperFrom.get("weektag"));
                		tm.setDirectAt((String) tempMapperFrom.get("codeDiv"));
                		tm.setOrgStnCd((String) tempMapperFrom.get("startStation"));
                		tm.setOrgStnNm((String) tempMapperFrom.get("startStationName"));
                		tm.setDstStnCd((String) tempMapperFrom.get("endStation"));
                		tm.setDstStnNm((String) tempMapperFrom.get("endStationName"));
                		tm.setUpdateDt((String) tempMapperFrom.get("basicDate"));
                    		
                		timeTableList.add(tm);
                    		
                    }
                    
                    timeTableService.insertTimetableMapping(timeTableList);
                    
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                
            } else {
                System.out.println(con.getResponseMessage());
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
		
		log.info("callAPIScheduledTimeTable() END AT[ {} ]", new Date());
	}
		
}
