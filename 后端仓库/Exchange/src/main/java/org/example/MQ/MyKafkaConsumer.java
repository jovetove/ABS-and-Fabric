//package org.example.MQ;
//import java.util.concurrent.ExecutorService;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//
//import java.time.LocalTime;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.concurrent.*;
//
//public class MyKafkaConsumer {
//    /**
//     * kafka消费者不是线程安全的
//     */
//    private final KafkaConsumer<String, String> consumer;
//
////    private ExecutorService executorService;
//    private ThreadPoolExecutor executor;
//
//    public MyKafkaConsumer() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers",
//                "ip,port");
//        props.put("group.id", "group");
//        // 关闭自动提交
//        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
//        props.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        consumer = new KafkaConsumer<String, String>(props);
//        consumer.subscribe(Arrays.asList("test_find"));
//    }
//
//    public void execute() {
//        executor = new ThreadPoolExecutor(4, 4, 60,
//                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10),
//                new MyThreadFactory("MyThreadFromPool"),
//                new ThreadPoolExecutor.AbortPolicy());
//
//
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(10);
//            if (null != records) {
////                executorService.submit(new ConsumerThread(records, consumer));
//            }
//        }
//    }
//
//    public void shutdown() {
//        try {
//            if (consumer != null) {
//                consumer.close();
//            }
//            if (executorService != null) {
//                executorService.shutdown();
//            }
//            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//                System.out.println("Timeout");
//            }
//        } catch (InterruptedException ignored) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private static class MyThreadFactory implements ThreadFactory {
//
//        private final String namePrefix;
//
//        public MyThreadFactory(String namePrefix) {
//            this.namePrefix = namePrefix;
//        }
//
//        public Thread newThread(Runnable runnable) {
//            return new Thread(runnable, namePrefix + "-" + threadNumber.getAndIncrement());
//        }
//
//    }
//}
