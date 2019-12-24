package com.chryl.dao;

import com.chryl.pojo.TaskHis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chryl on 2019/12/23.
 */
public interface TaskHisRepo extends JpaRepository<TaskHis, String> {
}
