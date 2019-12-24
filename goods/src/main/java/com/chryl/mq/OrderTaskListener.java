package com.chryl.mq;

import com.alibaba.fastjson.JSON;
import com.chryl.pojo.QueueNames;
import com.chryl.pojo.Task;
import com.chryl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听:商品扣库存的业务模块
 * 监听订单模块是否有下单情况,如果有下单成功的消息,减少库存
 * <p>
 * Created by Chryl on 2019/12/23.
 */
@Component
public class OrderTaskListener {

    @Autowired
    private GoodsService goodsService;

    //监听下单模块的下单队列
    @JmsListener(destination = QueueNames.SAVE_ORDER)
    public void recevieOrderTask(String taskStr) {//参数是 订单模块 传送的jsonTask
        System.out.println(taskStr);
        Task task = JSON.parseObject(taskStr, Task.class);
        //接到消息去扣库存
        goodsService.updateStore(task);
    }

}
