package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/8/2
 */

import com.haohua.erp.entity.Car;
import com.haohua.erp.entity.Customer;
import com.haohua.erp.entity.CustomerExample;
import com.haohua.erp.mapper.CarMapper;
import com.haohua.erp.mapper.CustomerMapper;
import com.haohua.erp.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 根据车牌号查询车辆信息
     * @param licenseNo 车牌号
     * @return
     */
    @Override
    public Car findCarWithCustomerInfo(String licenseNo) {
        return carMapper.findCarWithCustomerInfo(licenseNo) ;
    }

    /**
     * 根据车辆和车主信息录入
     * @param car
     * @param customer
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertInfo(Car car, Customer customer) {
        // 通过身份证号码查询客户是否存在
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(customer.getIdCard());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        // 如果客户不存在则添加客户信息，获得自动生成的主键
        Integer customerId=null;
        if (customerList.isEmpty()){
            customerMapper.insertSelective(customer);
            customerId=customer.getId();
        }else {
            customerId = customerList.get(0).getId();
        }
            //根据车主录入车辆信息
            car.setCustomerId(customerId);
            carMapper.insertSelective(car);
    }


}
