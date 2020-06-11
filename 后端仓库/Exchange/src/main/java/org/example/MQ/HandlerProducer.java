package org.example.MQ;

/**
 * @author Administrator
 */
public class HandlerProducer implements Runnable {

    private String message;

    public HandlerProducer(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        KafkaProducerSingleton kafkaProducerSingleton = KafkaProducerSingleton
                .getInstance();
        kafkaProducerSingleton.init("test_find",3);
        System.out.println("当前线程:" + Thread.currentThread().getName()
                + ",获取的kafka实例:" + kafkaProducerSingleton);
        kafkaProducerSingleton.sendKafkaMessage("发送消息" + message);
    }

}