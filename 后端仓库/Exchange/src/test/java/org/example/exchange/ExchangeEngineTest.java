package org.example.exchange;


import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


@Slf4j
public class ExchangeEngineTest {

    private ExchangeEngine exchangeEngine;

    @Before
    public void before(){
        exchangeEngine = ExchangeEngine.getExchangeEngine();
    }

    @After
    public void after(){
        exchangeEngine = null;
    }

    @Test
    public void getExchangeEngine() {
        if (exchangeEngine == null){
            log.info("交易系统为空");
            System.out.println("交易系统为空");
        }else{
            log.info("成功获取交易系统");
            System.out.println("成功获取交易系统");
        }
    }

    @Test
    public void addSymbol() {
        try {
            exchangeEngine.addSymbol("symbol_1");
            log.info("symbol_1 添加成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getOrderBook() {
        OrderBook book = null;
        addSymbol();
        try {
            book = exchangeEngine.getOrderBook("symbol_1");
            if (book != null){
                log.info("获取交易队列成功");
            }else {
                log.info("获取交易队列失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Order[] buyOrders = {
                //int amount, double price, String orderID, String type
                new Order(361,558,"buy_5","buy"),
                new Order(166,559,"buy_5","buy"),
                new Order(385,560,"buy_5","buy"),
                new Order(439,561,"buy_5","buy"),
                new Order(361,562,"buy_5","buy"),
        };
        Order[] sellOrders = {
                //int amount, double price, String orderID, String type
                new Order(591,563,"sell_1","sell"),
                new Order(2498,564,"sell_2","sell"),
                new Order(2851,565,"sell_3","sell"),
                new Order(649,566,"sell_4","sell"),
                new Order(351,567,"sell_5","sell"),
        };

        for(Order x:sellOrders){
            log.info(x.toString());
            book.addSell(x);
        }
        for(Order x:buyOrders){
            log.info(x.toString());
            book.addBuy(x);
        }

        book.addBuy(new Order(591,563,"buy_x","buy"));
        book.addBuy(new Order(91,563,"buy_x","buy"));
        book.addSell(new Order(100,563,"sell_x","sell"));
        book.getBook();
    }
}