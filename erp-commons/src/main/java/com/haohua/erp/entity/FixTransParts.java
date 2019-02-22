package com.haohua.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * fix_trans_parts
 * @author 
 */
public class FixTransParts implements Serializable {
    //1.未领取  2.已领取
    public static final String PARTS_STATE_UNRECEIVE="1";
    public static final String PARTS_STATE_RECEIVED="2";
    private Integer id;

    private String partsNo;

    private String partsName;

    private Integer partsNum;

    private String orderType;

    private String employeeName;

    private String state;

    private Integer orderId;

    private Date orderTime;

    private Date createTime;

    private List<FixTransParts> fixTransPartsListVo;
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

    public Integer getPartsNum() {
        return partsNum;
    }

    public void setPartsNum(Integer partsNum) {
        this.partsNum = partsNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FixTransParts other = (FixTransParts) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPartsNo() == null ? other.getPartsNo() == null : this.getPartsNo().equals(other.getPartsNo()))
            && (this.getPartsName() == null ? other.getPartsName() == null : this.getPartsName().equals(other.getPartsName()))
            && (this.getPartsNum() == null ? other.getPartsNum() == null : this.getPartsNum().equals(other.getPartsNum()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPartsNo() == null) ? 0 : getPartsNo().hashCode());
        result = prime * result + ((getPartsName() == null) ? 0 : getPartsName().hashCode());
        result = prime * result + ((getPartsNum() == null) ? 0 : getPartsNum().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getEmployeeName() == null) ? 0 : getEmployeeName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", partsNo=").append(partsNo);
        sb.append(", partsName=").append(partsName);
        sb.append(", partsNum=").append(partsNum);
        sb.append(", orderType=").append(orderType);
        sb.append(", employeeName=").append(employeeName);
        sb.append(", state=").append(state);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public  String getStateName(){
        if(getState().equals(PARTS_STATE_UNRECEIVE)){
            return "未领取";
        }else{
            return "已领取";
        }
    }

    public List<FixTransParts> getFixTransPartsListVo() {
        return fixTransPartsListVo;
    }

    public void setFixTransPartsListVo(List<FixTransParts> fixTransPartsListVo) {
        this.fixTransPartsListVo = fixTransPartsListVo;
    }
}