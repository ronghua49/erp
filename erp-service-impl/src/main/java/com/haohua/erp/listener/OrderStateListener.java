package com.haohua.erp.listener;    /*
 * @author  Administrator
 * @date 2018/8/8
 */

import com.haohua.erp.entity.Order;
import com.haohua.erp.entity.OrderEmployee;
import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.dto.OrderEmployeeDto;
import com.haohua.erp.mapper.OrderEmployeeMapper;
import com.haohua.erp.mapper.OrderMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderStateListener implements MessageListener {
    Logger logger = LoggerFactory.getLogger(OrderStateListener.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("---------------------");
            System.out.println(textMessage.getText());
            logger.debug("监听到新订单状态{}",textMessage.getText());



            JSONObject jsonObject = JSONObject.fromObject(textMessage.getText());
            OrderEmployeeDto orderEmployeeDto = (OrderEmployeeDto) JSONObject.toBean(jsonObject,OrderEmployeeDto.class);
            if(StringUtils.isNotEmpty(orderEmployeeDto.getEmployeeId().toString())){
                OrderEmployee orderEmployee = new OrderEmployee();
                orderEmployee.setEmployeeId(orderEmployeeDto.getEmployeeId());
                orderEmployee.setOrderId(orderEmployeeDto.getOrderId());
                orderEmployeeMapper.insert(orderEmployee);
        }

            Order order = orderMapper.selectByPrimaryKey(orderEmployeeDto.getOrderId());
            order.setState(orderEmployeeDto.getOrderState());
            orderMapper.updateByPrimaryKeySelective(order);
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
