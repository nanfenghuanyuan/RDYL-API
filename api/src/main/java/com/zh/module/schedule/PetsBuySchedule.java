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
	 * 宠物匹配
	 */
	@Scheduled(cron="40 21 12 * * ?")
	public void buys(){
		Integer level = 1;
		petsV2Biz.matching(level);
	}
	@Scheduled(cron="0 1 1 * * ?")
	public void clear(){
		petsV2Biz.clear();
	}


}
