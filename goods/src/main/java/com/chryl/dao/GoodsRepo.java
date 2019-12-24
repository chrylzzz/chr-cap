package com.chryl.dao;


import com.chryl.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Chryl on 2019/12/23.
 */
public interface GoodsRepo extends JpaRepository<Goods, Integer> {

    @Modifying//注解提示 JPA 该操作是修改操作。
    @Query("update Goods g set g.count = g.count - ?2 where g.goodId = ?1")//JPQL语句: ?2 代表方法形参第二个参数,?1 代表形参第一个参数
    void updateStoreByGoodId(Integer goodId, Integer num);

}
