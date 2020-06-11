package org.example.exchange;

import java.util.HashMap;

interface ExchangeEngineInterface{
    /**
     * 新增证券
     * @param name
     */
    void addSymbol(String name);

    /**
     * 获取 name 证券的交易book
     * @param name
     * @return
     */
    OrderBook getOrderBook(String name);

}

/**
 * @author Administrator
 */
public class ExchangeEngine implements  ExchangeEngineInterface{
    private  HashMap<String, OrderBook> symbolMap;
    public static volatile ExchangeEngine exchangeEngine;

    public ExchangeEngine(){
        symbolMap = new HashMap<>();
    }

    public static ExchangeEngine getExchangeEngine(){
        if(exchangeEngine == null){
            synchronized (ExchangeEngine.class){
                if(exchangeEngine == null){
                    exchangeEngine = new ExchangeEngine();
                }
            }
        }
        return exchangeEngine;
    }


    /**
     * 新建一个证券类型
     * @param name 证券名称
     */
    @Override
    public void addSymbol(String name){
        if(!symbolMap.containsKey(name)){
            symbolMap.put(name, new OrderBook(name));
        }
    }

    @Override
    public OrderBook getOrderBook(String name) {
        if(!symbolMap.containsKey(name)){
            return null;
        }else {
            return symbolMap.get(name);
        }
    }
}
