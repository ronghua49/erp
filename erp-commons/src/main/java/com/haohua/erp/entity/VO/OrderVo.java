package com.haohua.erp.entity.VO;    /*
 * @author  Administrator
 * @date 2018/8/3
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderVo implements Serializable {

    private Integer orderId;
    private Integer carId;
    private Integer serviceTypeId;
    private BigDecimal fee;
    private List<PartsVo> partsLists;

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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public List<PartsVo> getPartsLists() {
        return partsLists;
    }

    public void setPartsLists(List<PartsVo> partsLists) {
        this.partsLists = partsLists;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    @Override
    public String toString() {
        return "OrderVo{" +
                "orderId=" + orderId +
                ", carId=" + carId +
                ", serviceTypeId=" + serviceTypeId +
                ", fee=" + fee +
                ", partsLists=" + partsLists +
                '}';
    }
}
