package com.chryl.service;

import com.chryl.dao.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 轮训task表,找到扣除库存失败的订单
 * <p>
 * Created by Chryl on 2019/12/24.
 */
@Service
public class TimmerService {

    @Autowired
    private TaskRepo taskRepo;

    @Scheduled()
    public void show(){

    }
}
