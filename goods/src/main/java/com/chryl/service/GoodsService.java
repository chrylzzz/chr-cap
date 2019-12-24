package com.chryl.service;

import com.alibaba.fastjson.JSON;
import com.chryl.dao.GoodsRepo;
import com.chryl.dao.TaskHisRepo;
import com.chryl.pojo.*;
import com.chryl.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 收到订单模块的消息之后,减库存,已收到的消息存入消息表,放入一个单体事务,一切操作成功之后,发送扣除库存成功的消息
 * <p>
 * Created by Chryl on 2019/12/24.
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;
    @Autowired
    private TaskHisRepo taskHisRepo;

    @Autowired
    private JmsTemplate jmsTemplate;//activeMq的消息模板

    //扣除库存,接受的是订单模块传递的任务消息
    @Transactional
    public void updateStore(Task task) {
        /**
         * 保证消息的幂等性,重复消息只进行扣减一次库存
         */
        //根据传递的消息id,去库存模块表查看是否有该id的记录 : 根据task的 Id 获取 taskHis
        Optional<TaskHis> hisOptional = taskHisRepo.findById(task.getId());
        if (hisOptional.isPresent()) {
            //已经有历史消息,该消息已经被处理过了
//            throw new RuntimeException("该消息已经被处理过");
            return;
        }

        //1.根据消息的数据 减库存
        String requestBody = task.getRequestBody();
        //根据订单 获取具体的商品去扣减库存
        List<OrderItem> orderItems = JSON.parseArray(requestBody, OrderItem.class);
        if (orderItems != null && orderItems.size() != 0) {
            for (OrderItem orderItem : orderItems) {
                Integer goodId = orderItem.getGoodId();
                Integer num = orderItem.getNum();
                //判断库存是否足够
                Optional<Goods> optional = goodsRepo.findById(goodId);
                if (optional.isPresent()) {//有该商品
                    Goods goods = optional.get();
                    if (goods.getCount() < num) {//库存是不够用
                        throw new RuntimeException("库存不足");
                    }
                }
                goodsRepo.updateStoreByGoodId(goodId, num);
            }
        }

        //2.写入消息表
        TaskHis taskHis = new TaskHis();
        BeanUtils.copyProperties(task, taskHis);//该订单需要保证消息的时间一致
        taskHisRepo.save(taskHis);

        //3.扣减库存成功,将成功的消息写回订单模块
        Task task_success = new Task();
        task_success.setId(task.getId());//注意id
        task_success.setCreateTime(DateUtil.formatDate(new Date()));
        task_success.setUpdateTime(DateUtil.formatDate(new Date()));
        task_success.setMqQueueName(QueueNames.UPDATE_COUNT);
        task_success.setRequestBody(task.getRequestBody());
        jmsTemplate.send(task_success.getMqQueueName(), new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(JSON.toJSONString(task_success));
            }
        });

    }
}
