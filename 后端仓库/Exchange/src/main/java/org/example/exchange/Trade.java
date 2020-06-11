package org.example.exchange;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class Trade {
    private String buyOrderID;
    private String sellOrderID;
    private int amount;
    private int price;
    private long time;


    public Trade(String buyOrderID, String sellOrderID, int amount, int price) {
        this.buyOrderID = buyOrderID;
        this.sellOrderID = sellOrderID;
        this.amount = amount;
        this.price = price;
        this.time = System.nanoTime();
    }

    @Override
    public String toString(){
        Object json = JSONArray.toJSON(this);
        return json.toString();
    }
}
