package com.chryl.dao;


import com.chryl.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chryl on 2019/12/23.
 */
public interface GoodsRepo extends JpaRepository<Goods, String> {
}
