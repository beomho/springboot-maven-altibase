package com.app.main.altibase.mapper;

import com.app.main.altibase.vo.TimeTableMappingVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper       //Mybatis Mapper임을 명시 하는 어노테이션
public interface TimeTableMapper {

    //전체 리스트 조회
	public void insertTimetableMapping(TimeTableMappingVo timeTable) throws Exception;

    public void deleteTimetableMapping() throws Exception;
}
