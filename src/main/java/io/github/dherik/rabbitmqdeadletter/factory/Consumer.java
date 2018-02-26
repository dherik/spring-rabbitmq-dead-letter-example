package io.github.dherik.rabbitmqdeadletter.factory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Consumer {

    @RabbitListener(queues = ConsumerConfiguration.SEND_EMAIL_QUEUE, containerFactory = "notRequeueRejected")
    public void listenMain(String in) {
        System.out.println("Received with error: " + in);
        throw new RuntimeException("Some unexpected or business exception");
    }

    @RabbitListener(queues = ConsumerConfiguration.SEND_EMAIL_QUEUE_DLQ)
    public void listenDlq(String in) {
        System.out.println("ReceivedFromDLQ: " + in);
    }

}
