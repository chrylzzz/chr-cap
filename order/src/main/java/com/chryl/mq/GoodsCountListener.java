package com.chryl.mq;

import com.alibaba.fastjson.JSON;
import com.chryl.pojo.QueueNames;
import com.chryl.pojo.Task;
import com.chryl.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 监听库存模块 是否发送扣除库存成功的消息
 * <p>
 * Created by Chryl on 2019/12/24.
 */
@Component
public class GoodsCountListener {

    @Autowired
    private TaskService taskService;

    //监听库存模块 的队列
    @JmsListener(destination = QueueNames.UPDATE_COUNT)
    public void receiveGoodsCountTask(String taskStr) {
        //1.收到库存扣除成功之后,把消息写入消息历史表,删除实时消息表中的数据
        Task task = JSON.parseObject(taskStr, Task.class);
        taskService.deleteCurrentTaskAndSaveTaskHis(task);

    }
}
