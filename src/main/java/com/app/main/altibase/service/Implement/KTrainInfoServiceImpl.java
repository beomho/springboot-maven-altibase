package com.app.main.altibase.service.Implement;

import java.util.Date;
import java.util.List;
import com.app.main.altibase.mapper.KTrainInfoMapper;
import com.app.main.altibase.service.KTrainInfoService;
import com.app.main.altibase.vo.KTrainInfoVo;
import com.app.main.scheduler.SchedulerApp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service    //서비스 임을 명시
public class KTrainInfoServiceImpl implements KTrainInfoService {
    
	@Autowired
    private KTrainInfoMapper kTrainInfoMapper;

	@Override
	public void insertKTrainInfo(List<KTrainInfoVo> kTrainInfoVoList) throws Exception {
		
		if(kTrainInfoVoList!= null) {
			log.info("insertKTrainInfo {} ",kTrainInfoVoList.size());
			
			int i=0; 
			for(KTrainInfoVo kTrainInfoVo: kTrainInfoVoList) {
				
				kTrainInfoMapper.insertKTrainInfo(kTrainInfoVo);
				log.info("insertKTrainInfo {} ", i);
				i++;
			}
		}
	}

	@Override
	public void deleteKTrainInfo() throws Exception {
		kTrainInfoMapper.deleteKTrainInfo();
	}

    
}
