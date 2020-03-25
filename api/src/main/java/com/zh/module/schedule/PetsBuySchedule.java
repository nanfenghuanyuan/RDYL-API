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
	@Scheduled(cron="0 1 14 * * ?")
	public void buys(){
		Integer level = 2;
		petsV2Biz.matching(level);
	}
	/**
	 * 宠物匹配
	 */
	@Scheduled(cron="0 1 15 * * ?")
	public void buys1(){
		Integer level = 1;
		petsV2Biz.matching(level);
	}
	/**
	 * 宠物匹配
	 */
	@Scheduled(cron="0 1 16 * * ?")
	public void buys2(){
		Integer level = 3;
		petsV2Biz.matching(level);
	}
	/**
	 * 宠物匹配
	 */
	@Scheduled(cron="0 1 17 * * ?")
	public void buys4(){
		Integer level = 4;
		petsV2Biz.matching(level);
	}
	/**
	 * 宠物匹配
	 */
//	@Scheduled(cron="0 26 1 * * ?")
	public void buys5(){
		Integer level = 5;
		petsV2Biz.matching(level);
	}
	/**
	 * 宠物匹配
	 */
//	@Scheduled(cron="0 26 1 * * ?")
	public void buys6(){
		Integer level = 6;
		petsV2Biz.matching(level);
	}
	@Scheduled(cron="0 1 1 * * ?")
	public void clear(){
		petsV2Biz.clear();
	}


}
