package com.app.main.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.app.main.altibase.service.AltibaseService;
import com.app.main.altibase.vo.AltibaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Component
public class SchedulerApp {

	@Autowired
	private AltibaseService altibaseService;

	@Scheduled(cron = "0/10 * * * * *")
	public void alert(){
		log.info("{} ", new Date());
	
	List<AltibaseVo> altiList = altibaseService.list();
	

		if(altiList != null){
			for(int i=0; i<altiList.size(); i++){

				System.out.println(altiList.get(i));
			}

		}
	}
}
