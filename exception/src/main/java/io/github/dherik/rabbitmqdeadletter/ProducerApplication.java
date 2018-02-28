package io.github.dherik.rabbitmqdeadletter;

import io.github.dherik.rabbitmqdeadletter.exception.Consumer;
import io.github.dherik.rabbitmqdeadletter.exception.ConsumerConfiguration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String SEND_EMAIL_QUEUE = "send.email";
    public static final String SEND_EMAIL_EVENT_RK = "email.event.create";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
        context.close();
    }

    @Autowired
    RabbitTemplate template;

    @Autowired
    AmqpAdmin admin;

    private final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void run(String... arg0) throws Exception {
        this.template.convertAndSend( EMAIL_EXCHANGE, SEND_EMAIL_EVENT_RK, "message-email-content");
        clearRabbit();
    }

    /**
     * Only for demonstration of RabbitMQ DLQ
     */
    private void clearRabbit() throws InterruptedException {
        this.latch.await(10, TimeUnit.SECONDS);
        this.admin.deleteExchange(EMAIL_EXCHANGE);
        this.admin.deleteQueue(SEND_EMAIL_QUEUE);
        this.admin.deleteQueue(ConsumerConfiguration.SEND_EMAIL_QUEUE_DLQ);
        this.admin.deleteQueue(EMAIL_EXCHANGE);
    }

    @Bean
    public TopicExchange dlx() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Consumer consumer() {
        return new Consumer();
    }

}
