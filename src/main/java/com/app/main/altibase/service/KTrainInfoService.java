package com.app.main.altibase.service;

import java.util.List;

import com.app.main.altibase.vo.KTrainInfoVo;

public interface KTrainInfoService {

	// 데이터 MERGE INTO
    public void insetKTrainInfo(List<KTrainInfoVo> kTrainInfoVoList) throws Exception;
    
    // 전날 데이터 삭제
    public void deleteKTrainInfo() throws Exception;
    
}
