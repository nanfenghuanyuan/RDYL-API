package com.zh.module.schedule;

import com.zh.module.biz.DailyCountBiz;
import com.zh.module.biz.PetsListBiz;
import com.zh.module.service.DailyCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 统计
 */
@Component
public class ConsumeSchedule {
	
	@Autowired
	private DailyCountBiz dailyCountBiz;

	/**
	 * 每日统计
	 */
	@Scheduled(cron="0 5 1 * * ?")
	public void getProfit(){
		dailyCountBiz.consume();
	}


}
