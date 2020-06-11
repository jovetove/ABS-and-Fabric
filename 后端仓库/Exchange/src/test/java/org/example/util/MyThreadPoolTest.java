package org.example.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class MyThreadPoolTest {

    @Before
    public void before(){

    }

    @After
    public void after(){

    }

    class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("zjianfa");
                System.out.println(Thread.currentThread().getName() + "-" + LocalTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getInstance() {
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



    }

    @Test
    public void getExecutorService() {
    }

    @Test
    public void shutdown() {
    }
}