package com.example.demobase.controller;


import com.example.demobase.utill.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjianfa
 * @date 2020/05/06
 */
interface OrderInterface{
    /**
     * 用户下一个订单
     * @return
     */
    public String placeOrder();

    /**
     * 用户取消订单
     * @return
     */
    public String cacelOrder();

    /**
     * 用户查询订单状态
     * @return
     */
    public String queryOrderStatus();

    /**
     * 查询所有订单
     * @return
     */
    public String orderQueue();
}

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController implements OrderInterface{


    @Override
    public String placeOrder() {
        return null;
    }

    @Override
    public String cacelOrder() {
        return null;
    }

    @Override
    public String queryOrderStatus() {
        return null;
    }

    @Override
    public String orderQueue() {
        return null;
    }
}