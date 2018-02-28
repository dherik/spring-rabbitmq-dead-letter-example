# spring-rabbitmq-dead-letter-example
Dead Letter implementation example using RabbitMQ and Spring.

# Build

You need a RabbitMQ running to be able to run the examples.

With a RabbitMQ running, just:

    mvn clean install

There are two examples of Consumer and Producer with DLQ. If you need more details, just read the Spring documentation section about [Exception Handling](https://docs.spring.io/spring-amqp/docs/latest-ga/reference/htmlsingle/#exception-handling)

# The exception: Using AmqpRejectAndDontRequeueException

Very useful to control errors from business rules. When you would like to reject the message, you can just throw the `AmqpRejectAndDontRequeueException`. Remember that any other exception will put the message again in the main queue, creating a infinite loop by default.

With the configuration to pass the rejected messages to a DLQ queue (as implemented in this project), the messages that cause a `AmqpRejectAndDontRequeueException` will be redirected to the DLQ, waiting for some action (to be analyzed, redelivered, etc)

Some quotes about the exception from Spring documentation:

> [...] you can throw a AmqpRejectAndDontRequeueException; this prevents message requeuing, regardless of the setting of the defaultRequeueRejected property. 

> [...] the listener can throw an AmqpRejectAndDontRequeueException to conditionally control this behavior.

# The DefaultRequeueRejected flag from SimpleMessageListenerContainer

This configuration is more safer than the `AmqpRejectAndDontRequeueException` if you have fear about errors with the content-type of the message. Listeners that throws any Exception (included `AmqpRejectAndDontRequeueException`) will be redirected to the DLQ queue.

Some quotes about the flag from Spring documentation:

> Setting defaultRequeueRejected to false will cause messages to be discarded (or routed to a dead letter exchange).

> [...] When a message that cannot be converted is encountered (for example an invalid content_encoding header), some exceptions are thrown before the message reaches user code. With defaultRequeueRejected set to true (default), such messages would be redelivered over and over.
