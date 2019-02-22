package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/8/3
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.*;
import com.haohua.erp.entity.dto.OrderTransDto;
import com.haohua.erp.entity.VO.OrderVo;
import com.haohua.erp.entity.VO.PartsVo;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.mapper.*;
import com.haohua.erp.service.OrderService;
import com.haohua.erp.util.Constant;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;
    @Autowired
    private  PartsStreamMapper partsStreamMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CountDailyOrderMapper dailyOrderMapper;

    /**
     * 根据订单详情 新增订单
     *
     * @param orderVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addOrder(OrderVo orderVo, Integer employeeId) {
        //增加订单到数据库
        Order order = new Order();
        order.setCarId(orderVo.getCarId());
        order.setOrderMoney(orderVo.getFee());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setState(Order.ORDER_STATE_NEW);
        orderMapper.insertSelective(order);
        Integer orderId = order.getId();
        //增加订单需求的零部件信息到数据库
        for (PartsVo partsVo : orderVo.getPartsLists()) {
            OrderParts orderParts = new OrderParts();
            orderParts.setNum(partsVo.getNum());
            orderParts.setOrderId(orderId);
            orderParts.setPartsId(partsVo.getId());
            orderPartsMapper.insertSelective(orderParts);
        }
        //增加订单和接待着的关系
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setEmployeeId(employeeId);
        orderEmployee.setOrderId(orderId);
        orderEmployeeMapper.insertSelective(orderEmployee);
    }

    /**
     * 查询所有的服务列表
     *
     * @return
     */
    @Override
    public List<ServiceType> findServiceTypeList() {
        return serviceTypeMapper.selectByExample(null);
    }

    /**
     * 查询所有的零件类型
     *
     * @return
     */
    @Override
    public List<Type> findPartsTypeList() {
        return typeMapper.selectByExample(null);
    }

    /**
     * 根据类型id查询零件集合
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Parts> findPartsListByTypeId(Integer typeId) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andInventoryGreaterThan(0).andTypeIdEqualTo(typeId);
        return partsMapper.selectByExample(partsExample);
    }

    /**
     * 根据参数查询已经完成的订单
     *
     * @param paramMap
     * @return
     */
    @Override
    public PageInfo<Order> findOrdersByParamMap(Map<String, Object> paramMap) {
        PageHelper.startPage((Integer) paramMap.get("p"), Constant.DEFAULT_PAGESIZA);

        if (StringUtils.isNotEmpty((String) paramMap.get("orderTime"))) {

            String orderTime = (String) paramMap.get("orderTime");
            String[] dates = orderTime.split("/");
            paramMap.remove("orderTime");
            paramMap.put("dateBegin", dates[0]);
            paramMap.put("dateEnd", dates[1]);
        }
        List<Order> orderList = orderMapper.findOrdersByParamMap(paramMap);
        
        return new PageInfo(orderList);
    }

    /**
     * 根据id查询order信息
     *
     * @param orderId
     * @return
     */
    @Override
    public Order findOrder(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 根据orderId查询车辆信息
     *
     * @param orderId
     * @return
     */
    @Override
    public Car findCarInfoByOrderId(Integer orderId) {
        return orderMapper.findCarWithCustomerInfo(orderId);
    }

    /**
     * 根据orderId 查询所用服务
     *
     * @param orderId
     * @return
     */
    @Override
    public ServiceType findServiceTypeByOrderId(Integer orderId) {
        return orderMapper.findServiceTypeByOrderId(orderId);
    }

    /**
     * 根据订单id查询所用的零部件信息
     *
     * @param orderId
     * @return
     */
    @Override
    public List<Parts> findPartsListByOrderId(Integer orderId) {
        return orderMapper.findPartsListByOrderId(orderId);
    }

    /**
     * 根据订单id删除订单
     *
     * @param orderId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delOrderById(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null) {
            if (order.getState().equals(Order.ORDER_STATE_NEW)) {
                List<Parts> partsList = orderMapper.findPartsListByOrderId(orderId);
                for (Parts parts : partsList) {
                    OrderPartsExample orderPartsExample = new OrderPartsExample();
                    orderPartsExample.createCriteria().andPartsIdEqualTo(parts.getId());
                    orderPartsMapper.deleteByExample(orderPartsExample);
                }
                //删除员工和订单的关系
                OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
                orderEmployeeExample.createCriteria().andOrderIdEqualTo(orderId);
                orderEmployeeMapper.deleteByExample(orderEmployeeExample);
                //删除订单

                orderMapper.deleteByPrimaryKey(orderId);
            } else {
                throw new ServiceException("该订单已下发不得删除");
            }
        } else {
            throw new ServiceException("订单不存在");
        }
    }
    /**
     * 根据修改后的订单信息 修改订单
     *
     * @param orderVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrder(OrderVo orderVo) {
        Order order = orderMapper.selectByPrimaryKey(orderVo.getOrderId());
        if (order != null) {
            if (order.getState().equals(Order.ORDER_STATE_NEW)) {
                Integer orderId = order.getId();
                //更新订单
                order.setCarId(orderVo.getCarId());
                order.setOrderMoney(orderVo.getFee());
                order.setServiceTypeId(orderVo.getServiceTypeId());
                orderMapper.updateByPrimaryKeySelective(order);
                //删除对应order_parts列表
                List<Parts> partsList = orderMapper.findPartsListByOrderId(orderId);
                for (Parts parts : partsList) {
                    OrderPartsExample orderPartsExample = new OrderPartsExample();
                    orderPartsExample.createCriteria().andPartsIdEqualTo(parts.getId());
                    orderPartsMapper.deleteByExample(orderPartsExample);
                }
                //更新订单需求的零部件信息
                for (PartsVo partsVo : orderVo.getPartsLists()) {

                    OrderParts orderParts = new OrderParts();
                    orderParts.setNum(partsVo.getNum());
                    orderParts.setOrderId(orderId);
                    orderParts.setPartsId(partsVo.getId());
                    orderPartsMapper.insertSelective(orderParts);
                }
            } else {
                throw new ServiceException("该订单已下发不得修改");
            }
        } else {
            throw new ServiceException("订单不存在");
        }
    }

    /**
     * 查找所有状态的订单
     *
     * @return
     */
    @Override
    public List<Order> findAllOrder() {
        return orderMapper.selectByExample(null);
    }
    /**
     * 根据订单id下发订单
     * @param orderId
     */
    @Override
    public void transOrderById(Integer orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order!=null&&(order.getState().equals(Order.ORDER_STATE_NEW))){
            order.setState(Order.ORDER_STATE_TRANSED);
            orderMapper.updateByPrimaryKeySelective(order);
            //查询需要发送到消息队列的对象信息
            Car car = orderMapper.findCarWithCustomerInfo(orderId);
            List<Parts> partsList = orderMapper.findPartsListByOrderId(orderId);
            ServiceType serviceType = orderMapper.findServiceTypeByOrderId(orderId);
            order.setCar(car);
            order.setServiceType(serviceType);
            //封装信息
            OrderTransDto orderTransDto = new OrderTransDto();
            orderTransDto.setPartsList(partsList);
            orderTransDto.setOrder(order);
            //发送信息到消息队列
            String orderTransVOJson = JSONObject.fromObject(orderTransDto).toString();
            jmsTemplate.send("newOrder", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(orderTransVOJson);
                }
            });
        }else{
            throw  new ServiceException("订单不存在或已经下发，不得二次下发");
        }
    }

    /**
     * 统计前一天的订单总数和金额
     */
    @Override
    public void countDailyOrder() {
        //获得昨天日期
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime now = new DateTime();
        DateTime yesterdayDateTime= now.minusDays(1);
        String yesterdayDate = dateTimeFormatter.print(yesterdayDateTime);

        //昨天的java.date类型 日期
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        //查询昨天日期中的已经完成的订单
        List<Order> yestardayOrders= orderMapper.findYestardayOrder(Order.ORDER_STATE_DONE,yesterdayDate);
            CountDailyOrder dailyOrder = new CountDailyOrder();
        //统计一天中订单的数量和总金额
        if(!yestardayOrders.isEmpty()){
            dailyOrder.setOrderNum(yestardayOrders.size());
            BigDecimal totalMoney = BigDecimal.ZERO;
            for(Order order: yestardayOrders){
                  totalMoney=totalMoney.add(order.getOrderMoney());
            }
            dailyOrder.setOrderMoney(totalMoney);
            try {
                dailyOrder.setOrderDate(sf.parse(yesterdayDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dailyOrderMapper.insertSelective(dailyOrder);
        }else {
            try {
                dailyOrder.setOrderDate(sf.parse(yesterdayDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dailyOrderMapper.insertSelective(dailyOrder);

        }
    }
}
