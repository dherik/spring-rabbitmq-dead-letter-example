package io.github.dherik.rabbitmqdeadletter.factory;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {

    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_EXCHANGE_DLX_RK = "email.exchangeRK";

    static final String SEND_EMAIL_QUEUE_DLQ = "send.email.dlq";
    static final String SEND_EMAIL_QUEUE = "send.email";
    
    public static final String EMAIL_EVENT_CREATE_RK = "email.event.create";

    @Bean
    public Binding queueBinding() {
        return BindingBuilder
                .bind(main())
                .to(dlx())
                .with(EMAIL_EVENT_CREATE_RK);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(dlq())
                .to(dlx())
                .with(EMAIL_EXCHANGE_DLX_RK);
    }

    @Bean
    public Queue dlq() {
        return new Queue(SEND_EMAIL_QUEUE_DLQ);
    }


    @Bean
    public TopicExchange dlx() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Queue main() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", EMAIL_EXCHANGE);
        args.put("x-dead-letter-routing-key", EMAIL_EXCHANGE_DLX_RK);
        return new Queue(SEND_EMAIL_QUEUE, true, false, false, args);
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> notRequeueRejected(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

}
