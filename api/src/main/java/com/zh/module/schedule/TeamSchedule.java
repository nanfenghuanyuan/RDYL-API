package com.zh.module.schedule;

import com.zh.module.biz.DailyCountBiz;
import com.zh.module.biz.TeamBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 统计
 */
@Component
public class TeamSchedule {
	
	@Autowired
	private TeamBiz teamBiz;

	/**
	 * 团队每日奖励分配
	 */
	@Scheduled(cron="0 30 23 * * ?")
	public void getProfit(){
		teamBiz.getProfit();
	}


}
