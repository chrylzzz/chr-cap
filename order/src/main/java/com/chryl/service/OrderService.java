package com.chryl.service;

import com.alibaba.fastjson.JSON;
import com.chryl.dao.OrderItemRepo;
import com.chryl.dao.OrderRepo;
import com.chryl.dao.TaskRepo;
import com.chryl.pojo.*;
import com.chryl.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单业务层:用户页面下单,生成订单,通知商品库存模块进行减库存
 * <p>
 * Created by Chryl on 2019/12/23.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private JmsTemplate jmsTemplate;//activeMq的消息模板

    /**
     * 保存订单 ,第一大步,将前端发送的订单进行保存db,在将记录写入消息表,之后通过mq发送给分布式的其他业务模块
     * 但是本操作在这个 业务模块需要在一个原子操作中
     *
     * @param cart
     */
    @Transactional
    public void saveOrder(Cart cart) {
        /**
         * 一,保存订单
         */
        //1.创建订单对象,保存订单
        Order order = new Order();
        order.setOrderPayment(new BigDecimal(Math.random() * 1000));
        orderRepo.save(order);
        //2.从购物车 去除订单项,保存在关联表
        //orderList : 从前端传递过来,只带有good_id, 和num ,不带有order_id和id
        //orderItems 就是   [{good_id:1,num:1},{good_id:2,num:3}]
        List<OrderItem> orderItems = cart.getOrderItems();
        if (orderItems != null && orderItems.size() != 0) {
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrderId(order.getOrderId());
                orderItemRepo.save(orderItem);
            }
        }
        /**
         * 二,写入消息表
         */
        //1.创建消息对象
        Task task = new Task();
        task.setCreateTime(DateUtil.formatDate(new Date()));
        task.setUpdateTime(DateUtil.formatDate(new Date()));
        task.setMqQueueName(QueueNames.SAVE_ORDER);//队列名称
        //消息数据: [{good_id:1,num:1},{good_id:2,num:3}]
        task.setRequestBody(JSON.toJSONString(orderItems));//比较重要的字段,存的就是orderItemList
        //2.消息数据保存到db
        taskRepo.save(task);
        /**
         * 三,发送消息
         */
        jmsTemplate.send(task.getMqQueueName(), new MessageCreator() {//1,队列名 2,消息
            @Override
            public Message createMessage(Session session) throws JMSException {

                //taskObj -> jsonStr
                return session.createTextMessage(JSON.toJSONString(task));

            }
        });

    }
}
