package com.chryl.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车
 * <p>
 * Created by Chryl on 2019/12/23.
 */
public class Cart implements Serializable {

    private static final long serialVersionUID = -189545863020255128L;
    private String id;
    private List<OrderItem> orderItems;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
