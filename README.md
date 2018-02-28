# spring-rabbitmq-dead-letter-example
Dead Letter implementation example using RabbitMQ and Spring.

# Build

You need a RabbitMQ running to be able to run the examples.

With a RabbitMQ running, just:

    mvn clean install

There are two examples of Consumer and Producer with DLQ. If you need more details, just read the Spring documentation section about [Exception Handling](https://docs.spring.io/spring-amqp/docs/latest-ga/reference/htmlsingle/#exception-handling)

# The exception: Using AmqpRejectAndDontRequeueException

> [...] you can throw a AmqpRejectAndDontRequeueException; this prevents message requeuing, regardless of the setting of the defaultRequeueRejected property. 

> [...] the listener can throw an AmqpRejectAndDontRequeueException to conditionally control this behavior.

# The DefaultRequeueRejected flag from SimpleMessageListenerContainer

> Setting defaultRequeueRejected to false will cause messages to be discarded (or routed to a dead letter exchange).

> [...] When a message that cannot be converted is encountered (for example an invalid content_encoding header), some exceptions are thrown before the message reaches user code. With defaultRequeueRejected set to true (default), such messages would be redelivered over and over.
