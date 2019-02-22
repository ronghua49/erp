package com.haohua.erp.entity.dto;    /*
 * @author  Administrator
 * @date 2018/8/8
 */

import com.haohua.erp.entity.FixOrder;
import com.haohua.erp.entity.FixParts;
import com.haohua.erp.entity.Order;
import com.haohua.erp.entity.Parts;

import java.util.List;

public class OrderTransDto {
    private Order order;
    private List<Parts> partsList;
    private FixOrder fixOrder;
    private List<FixParts> fixPartsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    public List<FixParts> getFixPartsList() {
        return fixPartsList;
    }

    public void setFixPartsList(List<FixParts> fixPartsList) {
        this.fixPartsList = fixPartsList;
    }

    public FixOrder getFixOrder() {
        return fixOrder;
    }

    public void setFixOrder(FixOrder fixOrder) {
        this.fixOrder = fixOrder;
    }

    @Override
    public String toString() {
        return "OrderTransDto{" +
                "order=" + order +
                ", partsList=" + partsList +
                ", fixOrder=" + fixOrder +
                ", fixPartsList=" + fixPartsList +
                '}';
    }
}
