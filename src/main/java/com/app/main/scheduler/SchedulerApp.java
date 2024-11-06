package com.app.main.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.main.altibase.service.KTrainInfoService;
import com.app.main.altibase.vo.KTrainInfoVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerApp {

	@Autowired
	private KTrainInfoService kTrainInfoService;

//	@Scheduled(cron = "0 0 5 * * *") 
	@Scheduled(cron = "0 55 * * * *") 
	public void scheduled05() throws Exception{
		deleteData();
		callAPI("1");
		callAPI("4");
	}
	
	@Scheduled(cron = "0 0 17 * * *") 
	public void scheduled17() throws Exception{
		callAPI("1");
		callAPI("4");
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

        	kTrainInfoService.insetKTrainInfo(kTrainInfoList);
            
        }
        
        log.info("callAPI("+targetLine+") END AT[ {} ]", new Date());
	}
	
		
}
