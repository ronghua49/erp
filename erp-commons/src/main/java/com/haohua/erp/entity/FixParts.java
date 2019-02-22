package com.haohua.erp.entity;

import java.io.Serializable;

/**
 * fix_parts
 * @author 
 */
public class FixParts implements Serializable {
    private Integer id;

    private String partsNo;

    private String partsName;

    private Integer num;

    private Integer orderId;

    private Integer partsId;

    private Integer employeeId;
    private String employeeName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartsNo() {
        return partsNo;
    }

    public void setPartsNo(String partsNo) {
        this.partsNo = partsNo;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "FixParts{" +
                "id=" + id +
                ", partsNo='" + partsNo + '\'' +
                ", partsName='" + partsName + '\'' +
                ", num=" + num +
                ", orderId=" + orderId +
                ", partsId=" + partsId +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}