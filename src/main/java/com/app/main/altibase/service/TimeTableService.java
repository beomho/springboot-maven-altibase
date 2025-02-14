package com.app.main.altibase.service;

import java.util.List;

import com.app.main.altibase.vo.TimeTableMappingVo;

public interface TimeTableService {

	// 데이터 MERGE INTO
    public void insertTimetableMapping(List<TimeTableMappingVo> timeTableList) throws Exception;
    
    // 전날 데이터 삭제
    public void deleteTimetableMapping() throws Exception;
    
}
