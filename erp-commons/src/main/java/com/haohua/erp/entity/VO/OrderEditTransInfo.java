package com.haohua.erp.entity.VO;    /*
 * @author  Administrator
 * @date 2018/8/6
 */

import com.haohua.erp.entity.Car;
import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.ServiceType;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * 修改订单的数据回显 对象数据封装
 */
public class OrderEditTransInfo implements Serializable {
    private Integer orderId;
    private Car car;
    private ServiceType chooseServiceType;
    private List<Parts> choosePartsList;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ServiceType getChooseServiceType() {
        return chooseServiceType;
    }

    public void setChooseServiceType(ServiceType chooseServiceType) {
        this.chooseServiceType = chooseServiceType;
    }

    public List<Parts> getChoosePartsList() {
        return choosePartsList;
    }

    public void setChoosePartsList(List<Parts> choosePartsList) {
        this.choosePartsList = choosePartsList;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderEditTransInfo{" +
                "orderId=" + orderId +
                ", car=" + car +
                ", chooseServiceType=" + chooseServiceType +
                ", choosePartsList=" + choosePartsList +
                '}';
    }
}
