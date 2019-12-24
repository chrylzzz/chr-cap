package com.chryl.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Chryl on 2019/12/23.
 */
@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {


    private static final long serialVersionUID = 8189369898239566265L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "good_id")
    private Integer goodId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "num")
    private Integer num;


    public OrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
