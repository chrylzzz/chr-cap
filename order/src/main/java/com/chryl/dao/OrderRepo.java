package com.chryl.dao;

import com.chryl.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chryl on 2019/12/23.
 */
public interface OrderRepo extends JpaRepository<Order,Integer> {
}
