package org.example.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
public class MyThreadPool {
    private static class SingletonHolder{
        private static final MyThreadPool myThreadPool = new MyThreadPool();
    }
    private ExecutorService executorService;

    private int corePoolSize = 4;
    private int maximumPoolSize = 4;
    private int keepAliveTime = 60;
    private static final AtomicInteger threadNumber = new AtomicInteger(1);


    private static class MyThreadFactory implements ThreadFactory {
        private final String namePrefix;

        public MyThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, namePrefix + "-" + threadNumber.getAndIncrement());
        }
    }


    private MyThreadPool(){
        executorService = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(50),
                // 新线程的产生方式
                new MyThreadFactory("MyThreadFromPool"),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取线程池
     * @return
     */
    public static ExecutorService getInstance(){
        return SingletonHolder.myThreadPool.executorService;
    }
}
