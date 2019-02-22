package com.haohua.erp.timerTask;
/*
 * @author  Administrator
 * @date 2018/8/17
 */

import com.haohua.erp.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

public class CountDailyOrderJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            OrderService orderService = (OrderService) applicationContext.getBean("orderServiceImpl");
            orderService.countDailyOrder();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
