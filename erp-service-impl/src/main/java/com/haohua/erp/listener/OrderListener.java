package com.haohua.erp.listener;    /*
 * @author  Administrator
 * @date 2018/8/8
 */

import com.haohua.erp.entity.FixOrder;
import com.haohua.erp.entity.FixParts;
import com.haohua.erp.entity.Order;
import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.dto.OrderTransDto;
import com.haohua.erp.mapper.FixOrderMapper;
import com.haohua.erp.mapper.FixPartsMapper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderListener implements MessageListener {
    Logger logger = LoggerFactory.getLogger(OrderListener.class);

@Autowired
private FixOrderMapper fixOrderMapper;
@Autowired
private FixPartsMapper fixPartsMapper;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("---------------------");
            System.out.println(textMessage.getText());
            logger.debug("监听到新的下发订单{}",textMessage.getText());
            Map classMap = new HashMap();
            classMap.put("partsList",Parts.class);

            JSONObject jsonObject = JSONObject.fromObject(textMessage.getText());
            OrderTransDto orderTransDto = (OrderTransDto) JSONObject.toBean(jsonObject,OrderTransDto.class,classMap);
            System.out.println("----------解析后的对象----------");
            System.out.println(orderTransDto);
            Order order = orderTransDto.getOrder();
            List<Parts> partsList = orderTransDto.getPartsList();

            //将下发的订单数据插入数据库
            FixOrder fixOrder = new FixOrder();
            fixOrder.setOrderId(order.getId());
            fixOrder.setCarColor(order.getCar().getColor());
            fixOrder.setCarType(order.getCar().getCarType());
            fixOrder.setCarLicence(order.getCar().getLicenceNo());
            fixOrder.setCustomerName(order.getCar().getCustomer().getUserName());
            fixOrder.setCustomerTel(order.getCar().getCustomer().getTel());
            fixOrder.setState(order.getState());
            fixOrder.setOrderMoney(order.getOrderMoney());
            fixOrder.setOrderServiceHour(order.getServiceType().getServiceHour());
            fixOrder.setOrderServiceHourFee(BigDecimal.valueOf(Integer.parseInt(order.getServiceType().getServiceHour())*50));
            fixOrder.setOrderPartsFee(order.getOrderMoney().subtract(BigDecimal.valueOf(Integer.parseInt(order.getServiceType().getServiceHour())*50)));
            fixOrder.setOrderTime(order.getCreateTime());
            fixOrder.setOrderType(order.getServiceType().getServiceName());
            System.out.println("======================封装到数据库的fixOrder=========================");
            System.out.println(fixOrder);
            fixOrderMapper.insertSelective(fixOrder);
            //插入所需配件到数据库
            for(Parts parts:partsList){
                FixParts fixParts = new FixParts();
                fixParts.setNum(parts.getNum());
                fixParts.setOrderId(order.getId());
                fixParts.setPartsName(parts.getPartsName());
                fixParts.setPartsNo(parts.getPartsNo());
                fixParts.setPartsId(parts.getId());
                fixPartsMapper.insertSelective(fixParts);
            }

            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
