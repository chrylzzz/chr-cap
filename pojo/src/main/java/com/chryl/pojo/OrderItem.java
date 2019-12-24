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
    private String id;

    @Column(name = "good_id")
    private String goodId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "num")
    private String num;


    public OrderItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
