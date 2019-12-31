package com.zh.module.schedule;

import com.zh.module.biz.PetsListBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProfitSchedule {
	
	@Autowired
	private PetsListBiz petsListBiz;


	/**
	 * 收益结算
	 */
	@Scheduled(cron="0 1 * * * ?")
	public void getProfit(){
		petsListBiz.getProfit();
	}

}
