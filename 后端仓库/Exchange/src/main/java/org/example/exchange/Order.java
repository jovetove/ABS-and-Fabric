package org.example.exchange;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 类注释
 * @author zjianfa
 * @date 2020/05/06
 */
@Data
public class Order {
    private int amount;
    private int price;
    private long time;
    private String orderID;
    private String type;

    /**
     * 构造新的订单
     * @param amount  数量
     * @param price 价格
     * @param orderID id号
     * @param type 类型
     */
    public Order(int amount, int price, String orderID, String type) {
        this.amount = amount;
        this.price = price;
        this.time = System.nanoTime();
        this.orderID = orderID;
        this.type = type;
    }

    @Override
    public String toString(){
        Object json = JSONArray.toJSON(this);
        return json.toString();
    }
}
