package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/8/3
 */

import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.*;
import com.haohua.erp.entity.VO.OrderVo;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 根据订单详情 新增订单
     * @param orderVo
     * @param emloyeeId
     */
    void addOrder(OrderVo orderVo,Integer emloyeeId);

    /**
     * 查询所有的服务列表
     * @return
     */
    List<ServiceType> findServiceTypeList();

    /**
     * 查询所有的零件类型
     * @return
     */
    List<Type> findPartsTypeList();

    /**
     * 根据类型id插叙零件集合
     * @param typeId
     * @return
     */
    List<Parts> findPartsListByTypeId(Integer typeId);

    /**
     * 根据参数查询已经完成的订单
     * @param paramMap
     * @return
     */
    PageInfo<Order> findOrdersByParamMap(Map<String,Object> paramMap) ;

    /**
     * 根据id查询order信息
     * @param orderId
     * @return
     */
    Order findOrder(Integer orderId);

    /**
     * 根据orderId查询车辆信息
     * @param orderId
     * @return
     */
    Car findCarInfoByOrderId(Integer orderId);

    /**
     * 根据orderId 查询所用服务
     * @param orderId
     * @return
     */
    ServiceType findServiceTypeByOrderId(Integer orderId);

    /**
     * 根据订单id查询所用的零部件信息
     * @param orderId
     * @return
     */
    List<Parts> findPartsListByOrderId(Integer orderId);

    /**
     * 根据订单id删除订单
     * @param orderId
     */
    void delOrderById(Integer orderId);

    /**
     * 根据修改后的订单信息和订单id 修改订单
     * @param orderVo
     */
    void editOrder(OrderVo orderVo);

    /**
     * 查找所有状态的订单
     * @return
     */
    List<Order> findAllOrder();

    /**
     * 根据订单id下发订单
     * @param orderId
     */
    void transOrderById(Integer orderId);

    /**
     * 统计前一天的订单总数和金额
     */
    void countDailyOrder();
}
