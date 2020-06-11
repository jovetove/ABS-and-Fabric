package com.example.demobase.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池配置信息
 * @author Administrator
 */
@Configuration
@EnableAsync
public class AsyncThreadPoolConfig implements AsyncConfigurer {
    //获取当前机器CPU数量
    private static final int cpu = Runtime.getRuntime().availableProcessors();
    // 核心线程数（默认线程数）
    private static final int corePoolSize = cpu;
    // 最大线程数
    private static final int maxPoolSize = cpu * 2;
    // 允许线程空闲时间（单位：默认为秒）
    private static final int keepAliveTime = 60;
    // 缓冲队列数
    private static final int queueCapacity = 200;
    // 线程池名前缀
    private static final String threadNamePrefix = "asyncThreadPool_taskExecutor-";

    /**
     *   taskExecutor 为 bean的名称，默认为首字母小写的方法名
     * @return
     */
    @Override
    @Bean(name = "asyncThreadPool")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置请求上下文的传递!
        // executor.setTaskDecorator(new MdcTaskDecorator());
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
