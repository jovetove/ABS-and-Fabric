package org.example.exchange;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Administrator
 */
@Slf4j
public class OrderBook {
    private String symbolID;
    private volatile int currentPrice = 0;
    private PriorityBlockingQueue<Order> buy;
    private PriorityBlockingQueue<Order> sell;

    /**
     * 构造一个订单队列
     * buy 队列 表示买的订单 降序
     * sell 队列 表示卖的订单 升序
     * @param symbolID
     */
    public OrderBook(String symbolID){
        this.symbolID = symbolID;
        buy = new PriorityBlockingQueue<Order>(10,(o1, o2) -> {
            if (o1.getPrice() == o2.getPrice()){
                if(o1.getTime() > o2.getTime()){
                    return -1;
                }else{
                    return 1;
                }
            }
            if(o1.getPrice() > o2.getPrice()){
                return -1;
            }else{
                return 1;
            }

        });
        sell = new PriorityBlockingQueue<Order>(10,(o1, o2) -> {
            if (o1.getPrice() == o2.getPrice()){
                if(o1.getTime() > o2.getTime()){
                    return 1;
                }else{
                    return -1;
                }
            }
            if(o1.getPrice() > o2.getPrice()){
                return 1;
            }else{
                return -1;
            }
        });
    }


    public List<Trade> addBuy(Order order){
        List<Trade> tradeList = new ArrayList<>();
        if(sell.isEmpty()){
            buy.offer(order);
            
            log.info("加入到买的队列");
            currentPrice = order.getPrice();
        }else{
            if(order.getPrice() < sell.peek().getPrice() ){
                log.info("当前买的价格小于卖一价，加入到买的队列"+ " 买一: " +order.getPrice() + " 卖一: " +sell.peek().getPrice());
                buy.offer(order);
            }else {
                int buyAmount = order.getAmount();
                // 不断的从卖的队列中取出，然后减少相应的数量
                // 当订单成交完，或者卖的队列已经没有小于或者等于它的价格
                while (buyAmount > 0 && order.getPrice() >= sell.peek().getPrice()){
                    Order sellOrder1 = sell.poll();
                    if(buyAmount >= sellOrder1.getAmount()){
                        Trade trade = new Trade(order.getOrderID(),sellOrder1.getOrderID(),
                                sellOrder1.getAmount(),sellOrder1.getPrice());
                        tradeList.add(trade);
                        log.info("成交:" + trade.toString() + " 当前价格:"+ currentPrice);
                        buyAmount -= sellOrder1.getAmount();
                    }else{
                        sellOrder1.setAmount(sellOrder1.getAmount() - buyAmount);
                        this.addSell(sellOrder1);

                        Trade trade = new Trade(order.getOrderID(),sellOrder1.getOrderID(),
                                order.getAmount(), sellOrder1.getPrice());
                        tradeList.add(trade);
                        log.info("成交:" + trade.toString() + " 当前价格:"+ currentPrice);
                        buyAmount = 0;
                    }
                    currentPrice = sellOrder1.getPrice();
                }

            }
        }
        return tradeList;
    }

    public List<Trade> addSell(Order order){
        List<Trade> tradeList = new ArrayList<>();
        if(buy.isEmpty()){
            sell.offer(order);
            log.info("加入到卖的队列");
            currentPrice = order.getPrice();
        }else {
            if(order.getPrice() > buy.peek().getPrice()){
                log.info("当前卖的价格大于买一价，加入到卖的队列");
                sell.offer(order);
            }else{
                int sellAmount = order.getAmount();
                while (sellAmount > 0 && order.getPrice() >= buy.peek().getPrice()){
                    Order buyOrder1 = buy.poll();
                    if(sellAmount >= buyOrder1.getAmount()){
                        Trade trade = new Trade(order.getOrderID(),buyOrder1.getOrderID(),
                                buyOrder1.getAmount(), buyOrder1.getPrice());
                        tradeList.add(trade);
                        log.info("成交:" + trade.toString() + " 当前价格:"+ currentPrice);
                        sellAmount -= buyOrder1.getAmount();
                    }else{
                        buyOrder1.setAmount(buyOrder1.getAmount() - sellAmount);
                        this.addBuy(buyOrder1);
                        Trade trade = new Trade(order.getOrderID(),buyOrder1.getOrderID(),
                                order.getAmount(), buyOrder1.getPrice());
                        tradeList.add(trade);
                        log.info("成交:" + trade.toString() + " 当前价格:"+ currentPrice);
                        sellAmount = 0;
                    }
                    currentPrice = buyOrder1.getPrice();
                }
            }
        }
        return tradeList;
    }

    public void reomvenBuyOrder(Order order){
        buy.remove(order);
    }

    public void reomvenSellOrder(Order order){
        buy.remove(order);
    }

    public double getCurrentPrice(){
        return currentPrice;
    }

    public List<List<Order>> getBook(){
        Iterator<Order> iteratorBuy = buy.iterator();
        Iterator<Order> iteratorSell = sell.iterator();
        List<Order> listBuy = new ArrayList<>(5);
        List<Order> listSell = new ArrayList<>(5);

        int i = 0;
        while (iteratorBuy.hasNext() && i < 5){
            listBuy.add(iteratorBuy.next());
            i++;
        }

        int j = 0;
        while (iteratorSell.hasNext() && j < 5){
            listSell.add(iteratorSell.next());
            j++;
        }
        List<List<Order>> list = new ArrayList<>();
        list.add(listBuy);
        list.add(listSell);
        log.info(listBuy.toString());
        log.info(listSell.toString());
        return list;
    }
}
