package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/8/8
 */

import com.haohua.erp.entity.Employee;
import com.haohua.erp.entity.FixOrder;

import java.util.List;

public interface FixOrderService {

    /**
     * 订单接受，领取配件
     * @param orderId
     */
    void acceptOrder(Integer orderId,Employee employee);

    /**
     * 查询所有下发的维修订单和需求的零件
     * @return
     */
    List<FixOrder> findFixOrderListWithParts();

    /**
     * 根据维修订单id查询订单的详细信息
     * @param orderId
     * @return
     */
    FixOrder findFixOrderWithPartsById(Integer orderId);

    /**
     * 维修完成
     * @param orderId
     */
    void getFixDone(Integer orderId);

    /**
     * 查询所有等待质检的订单
     * @return
     */
    List<FixOrder> findCheckOrderListWithParts();

    /**
     * 领取质检任务
     * @param orderId
     * @param employee
     */
    void getCheckTask(Integer orderId,Employee employee);

    /**
     *根据订单id查询要质检的订单详情
     * @param orderId
     */
    FixOrder findCheckOrderWithPartsById(Integer orderId);


    /**
     * 接单开始记录任务时间
     * @param orderId 订单id
     * @param employeeId 接单员
     */
    void countTaskTime(Integer orderId, Integer employeeId);

    /**
     *记录超时任务
     * @param orderId
     * @param employeeId
     */
    void recordTimeOutTask(Integer orderId, Integer employeeId);

    /**
     *  删除定时任务
     * @param orderId
     * @param id
     */
    void deleteJob(Integer orderId, Integer id);
}
