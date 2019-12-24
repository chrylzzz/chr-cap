package com.chryl.service;

import com.chryl.dao.TaskHisRepo;
import com.chryl.dao.TaskRepo;
import com.chryl.pojo.Task;
import com.chryl.pojo.TaskHis;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 消息 保存到历史消息表taskHis,删除实时的消息task
 * <p>
 * Created by Chryl on 2019/12/24.
 */
@Service
public class TaskService {

    @Autowired
    private TaskHisRepo taskHisRepo;
    @Autowired
    private TaskRepo taskRepo;

    //删除实时表消息,保存消息到历史消息表
    @Transactional
    public void deleteCurrentTaskAndSaveTaskHis(Task task) {
        //1.添加到历史消息表
        TaskHis taskHis = new TaskHis();
        BeanUtils.copyProperties(task, taskHis);
        taskHisRepo.save(taskHis);
        //2.删除实时消息
        taskRepo.delete(task);
    }
}
