package com.chryl.mq;

import com.chryl.pojo.QueueNames;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听:商品扣库存的业务模块
 * 监听订单模块是否有下单情况,如果有下单,减少库存
 * <p>
 * Created by Chryl on 2019/12/23.
 */
@Component
public class OrderTaskListener {


    //监听下单模块的下单队列
    @JmsListener(destination = QueueNames.SAVE_ORDER)
    public void recevieOrderTask(String taskStr) {//参数是 订单模块 传送的jsonTask
        System.out.println(taskStr);

    }

}
