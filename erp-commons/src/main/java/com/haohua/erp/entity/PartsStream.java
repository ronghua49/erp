package com.haohua.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * parts_stream
 * @author 
 */
public class PartsStream implements Serializable {
    /**
     * 备件流水ID
     */
    private Integer id;

    /**
     * 备件ID
     */
    private Integer partsId;

    private Integer beforeNum;

    /**
     * 所需数量
     */
    private Integer num;

    private Integer afterNum;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 1.入库 2.出库
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;


    private String partsNo;
    private String partsName;
    private String employeeName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public Integer getBeforeNum() {
        return beforeNum;
    }

    public void setBeforeNum(Integer beforeNum) {
        this.beforeNum = beforeNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAfterNum() {
        return afterNum;
    }

    public void setAfterNum(Integer afterNum) {
        this.afterNum = afterNum;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "PartsStream{" +
                "id=" + id +
                ", partsId=" + partsId +
                ", beforeNum=" + beforeNum +
                ", num=" + num +
                ", afterNum=" + afterNum +
                ", employeeId=" + employeeId +
                ", type=" + type +
                ", createTime=" + createTime +
                ", partsNo='" + partsNo + '\'' +
                ", partsName='" + partsName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}