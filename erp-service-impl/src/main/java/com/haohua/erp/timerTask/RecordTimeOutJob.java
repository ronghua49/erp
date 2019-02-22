package com.haohua.erp.timerTask;    /*
 * @author  Administrator
 * @date 2018/8/19
 */

import com.haohua.erp.service.FixOrderService;
import com.haohua.erp.serviceImp.FixOrderServiceImpl;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RecordTimeOutJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Integer orderId = (Integer) jobDataMap.get("orderId");
        Integer employeeId = (Integer) jobDataMap.get("employeeId");

        jobExecutionContext.getJobDetail().getKey().getName();
        try {
            ApplicationContext context = (ApplicationContext) jobExecutionContext
                    .getScheduler().getContext().get("applicationContext");

            FixOrderService fixOrderService = context.getBean(FixOrderService.class);
            fixOrderService.recordTimeOutTask(orderId,employeeId);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
