package com.app.main.altibase.mapper;

import java.util.List;

import com.app.main.altibase.vo.AltibaseVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper       //Mybatis Mapper임을 명시 하는 어노테이션
public interface AltibaseMapper {

    //전체 리스트 조회
    public List<AltibaseVo> list();
    
}
