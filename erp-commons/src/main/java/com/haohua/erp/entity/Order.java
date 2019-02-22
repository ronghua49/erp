package com.haohua.erp.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order
 * @author 
 */
@Component
public class Order implements Serializable {



    /**
     * 订单状态 1：新订单 2.订单已下发 3：维修中  4.待质检 5：质检中 6：等待结算 7：完成
     */

    public static final String ORDER_STATE_NEW = "1";
    public static final String ORDER_STATE_TRANSED = "2";
    public static final String ORDER_STATE_DEALING = "3";
    public static final String ORDER_STATE_WITING_CHECK = "4";
    public static final String ORDER_STATE_CHECKING = "5";
    public static final String ORDER_STATE_SETTLEMENT= "6";
    public static final String ORDER_STATE_DONE = "7";

    private Integer id;

    /**
     * 订单总价
     */
    private BigDecimal orderMoney;

    /**
     * 订单状态 
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 车辆id
     */
    private Integer carId;
    /**
     * 工时费
     */
    private Integer serviceTypeId;

    private Car car;
    private ServiceType serviceType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOrderMoney() {
       return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

   public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderMoney=" + orderMoney +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", carId=" + carId +
                ", serviceTypeId=" + serviceTypeId +
                ", car=" + car +
                '}';
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

   public String getStateName() {

        //订单状态 1：新订单 2.已下发 3：维修中  4.待质检 5：质检中 6： 等待结算 7：完成
        if(getState().equals(ORDER_STATE_NEW)) {
           return  "新订单";
        } else if(state.equals(ORDER_STATE_DEALING)) {
            return "维修中";
        } else if(state.equals(ORDER_STATE_CHECKING)) {
            return "质检中";
        } else if(state.equals(ORDER_STATE_SETTLEMENT)) {
            return "等待结算中";
        } else if(state.equals(ORDER_STATE_DONE)) {
            return "完成";
        }else if (state.equals(ORDER_STATE_TRANSED)){
            return "订单已下发";
        }else if (state.equals(ORDER_STATE_WITING_CHECK)){
            return "待质检";
        }else{
             return "";
        }
    }

}