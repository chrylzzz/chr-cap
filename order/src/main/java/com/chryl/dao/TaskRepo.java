package com.chryl.dao;

import com.chryl.pojo.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chryl on 2019/12/23.
 */
public interface TaskRepo extends JpaRepository<Task, String> {
}
