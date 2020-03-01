package com.zh.module.schedule;

import com.zh.module.biz.DailyCountBiz;
import com.zh.module.biz.PetsV2Biz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 统计
 */
@Component
public class PetsBuySchedule {
	
	@Autowired
	private PetsV2Biz petsV2Biz;

	/**
	 * 每日统计
	 */
	@Scheduled(cron="0 1 1 * * ?")
	public void buys(){
		Integer level = 2;
		petsV2Biz.matching(2);
	}


}
