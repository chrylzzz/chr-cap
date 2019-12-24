package com.chryl.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Chryl on 2019/12/23.
 */
@Entity
@Table(name = "tb_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 975661898062574633L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "good_id")
    private String goodId;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "count")
    private String count;

    public Goods() {
    }

    public Goods(String title, BigDecimal price, String count) {
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
