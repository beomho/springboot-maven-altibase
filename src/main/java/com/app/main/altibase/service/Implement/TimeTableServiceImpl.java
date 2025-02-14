package com.app.main.altibase.service.Implement;

import java.util.Date;
import java.util.List;
import com.app.main.altibase.mapper.TimeTableMapper;
import com.app.main.altibase.service.TimeTableService;
import com.app.main.altibase.vo.TimeTableMappingVo;
import com.app.main.scheduler.SchedulerApp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service    //서비스 임을 명시
public class TimeTableServiceImpl implements TimeTableService {
    
	@Autowired
    private TimeTableMapper timeTableMapper;

	@Override
	public void insertTimetableMapping(List<TimeTableMappingVo> timeTableList) throws Exception {
		
		if(timeTableList!= null) {
			log.info("insertTimetableMapping {} ",timeTableList.size());
			
			timeTableMapper.deleteTimetableMapping();
			
			int i=0; 
			for(TimeTableMappingVo timeTable: timeTableList) {
				
				timeTableMapper.insertTimetableMapping(timeTable);
				log.info("insertTimetableMapping {} ", (i+1));
				i++;
			}
		}
	}

	@Override
	public void deleteTimetableMapping() throws Exception {
		timeTableMapper.deleteTimetableMapping();
	}

    
}
