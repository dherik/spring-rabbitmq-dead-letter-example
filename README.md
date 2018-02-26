# spring-rabbitmq-dead-letter-example
Dead Letter implementation example using RabbitMQ and Spring

You need a RabbitMQ running to be able to run the examples.

There are two examples of Consumer and Producer with DLQ:

1. Using AmqpRejectAndDontRequeueException exception
1. Using the flag DefaultRequeueRejected on SimpleMessageListenerContainer

The build is not working with `mvn clean install`, but the two ProducerApplication for each example is ok.
