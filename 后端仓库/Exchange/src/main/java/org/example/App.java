package org.example;


//import org.example.MQ.MyKafkaConsumer;

//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.example.exchange.Order;
import org.example.exchange.OrderBook;
import org.example.util.MyThreadPool;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;

/**
 * Hello world!
 *
 * @author Administrator
 */
@Slf4j
public class App {

    private static OrderBook book = new OrderBook("id_1");

    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                log.info("test");
                System.out.println(Thread.currentThread().getName() + "-" + LocalTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) {
        log.info("--------------Hello World!---------------");
        ExecutorService executorService = MyThreadPool.getInstance();
        // creates five tasks
        Task r1 = new Task();
        Task r2 = new Task();
        Task r3 = new Task();
        Task r4 = new Task();
        Task r5 = new Task();

        // submit方法有返回值
//        Future future = executorService.submit(r1);
//        System.out.println("r1 isDone ? " + future.isDone());

        // execute方法没有返回值
        executorService.execute(r2);
        executorService.execute(r3);
        executorService.execute(r4);
        executorService.execute(r5);

//    MyKafkaConsumer kafka_Consumer = new MyKafkaConsumer();
//        try {
//        kafka_Consumer.execute();
//        Thread.sleep(20000);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    } finally {
//        kafka_Consumer.shutdown();
//    }
    }


}
