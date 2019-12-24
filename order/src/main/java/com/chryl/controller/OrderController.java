package com.chryl.controller;

import com.chryl.pojo.Cart;
import com.chryl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chryl on 2019/12/23.
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @PostMapping("/save")
    public String saveOrder(@RequestBody Cart cart) {

        orderService.saveOrder(cart);

        return "ok";
    }
}
