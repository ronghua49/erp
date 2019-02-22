package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/8/2
 */

import com.haohua.erp.entity.Car;
import com.haohua.erp.entity.Customer;

public interface CarService {
    /**
     * 根据车牌号查询车辆信息
     * @param licenseNo 车牌号
     * @return
     */
    Car findCarWithCustomerInfo(String licenseNo);

    /**
     * 根据车辆和车主信息录入
     * @param car
     * @param customer
     */
    void insertInfo(Car car, Customer customer);


}
