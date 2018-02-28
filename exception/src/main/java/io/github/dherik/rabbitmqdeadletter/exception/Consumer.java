package io.github.dherik.rabbitmqdeadletter.exception;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Consumer {

    @RabbitListener(queues = ConsumerConfiguration.SEND_EMAIL_QUEUE)
    public void listenMain(String in) {
        System.out.println("Received with error: " + in);
        throw new AmqpRejectAndDontRequeueException("failed");
    }

    @RabbitListener(queues = ConsumerConfiguration.SEND_EMAIL_QUEUE_DLQ)
    public void listenDlq(String in) {
        System.out.println("ReceivedFromDLQ: " + in);
    }

}
