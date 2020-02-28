package com.zh.module.schedule;

import com.zh.module.biz.PetsListBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

	/**
	 * 自动取消预约
	 */
	@Scheduled(cron="1/5 * * * * ?")
	public void cancelAppointment(){
		petsListBiz.cancelAppointmentSchedule();
	}
	/**
	 * 自动取消未付款
	 */
	@Scheduled(cron="1/5 * * * * ?")
	public void cancelNoPay(){
		petsListBiz.cancelNoPaySchedule();
	}
	/**
	 * 自动确认未确认
	 */
	@Scheduled(cron="1/5 * * * * ?")
	public void cancelNoConfirm(){
		petsListBiz.cancelNoConfirmSchedule();
	}

	/**
	 * 待领养队列
	 */
	@PostConstruct
	@Scheduled(cron="0/30 * * * * ?")
	public void censusAppointment(){
		petsListBiz.censusAppointment();
	}

}
