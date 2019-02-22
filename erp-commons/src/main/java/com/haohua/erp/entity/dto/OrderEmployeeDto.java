package com.haohua.erp.entity.dto;    /*
 * @author  Administrator
 * @date 2018/8/9
 */

public class OrderEmployeeDto {
    private Integer orderId;
    private Integer employeeId;
    private String orderState;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        return "OrderEmployeeDto{" +
                "orderId=" + orderId +
                ", employeeId=" + employeeId +
                ", orderState='" + orderState + '\'' +
                '}';
    }
}
