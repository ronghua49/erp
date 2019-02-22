package com.haohua.erp.mapper;

import com.haohua.erp.entity.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据参数查询已经完成的订单信息
     *
     * @param paramMap
     * @return
     */
    default List<Order> findOrdersByParamMap(Map<String, Object> paramMap) {
        return null;
    }

    /**
     * 根据订单id查询车辆信息
     * @param orderId
     * @return
     */
    Car findCarWithCustomerInfo(Integer orderId);

    /**
     * 根据订单id查询所用服务
     * @param orderId
     * @return
     */
    ServiceType findServiceTypeByOrderId(Integer orderId);

    /**
     * 根据订单id查询所用零部件信息
     * @param orderId
     * @return
     */
    List<Parts> findPartsListByOrderId(Integer orderId);

    /**
     * 查询昨天已完成的订单
     * @param orderStateDone
     * @param yesterdayDate
     * @return
     */
    List<Order> findYestardayOrder(@Param("state") String orderStateDone, @Param("dateTime") String yesterdayDate);
}