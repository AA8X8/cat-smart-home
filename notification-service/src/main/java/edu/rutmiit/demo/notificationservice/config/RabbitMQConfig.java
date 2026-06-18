package edu.rutmiit.demo.notificationservice.config;

import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String NOTIFICATIONS_QUEUE = "q.notifications.cat-events";
    public static final String NOTIFICATIONS_DLQ = "q.notifications.cat-events.dlq";

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public TopicExchange eventsExchange() {
        return ExchangeBuilder.topicExchange(RoutingKeys.EXCHANGE).durable(true).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(RoutingKeys.EXCHANGE + ".dlx").durable(true).build();
    }

    @Bean
    public Queue notificationsQueue() {
        return QueueBuilder.durable(NOTIFICATIONS_QUEUE)
                .deadLetterExchange(RoutingKeys.EXCHANGE + ".dlx")
                .deadLetterRoutingKey(NOTIFICATIONS_DLQ)
                .build();
    }

    @Bean
    public Queue notificationsDlq() {
        return QueueBuilder.durable(NOTIFICATIONS_DLQ).build();
    }

    @Bean
    public Binding notificationsBinding(Queue notificationsQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(notificationsQueue)
                .to(eventsExchange)
                .with(RoutingKeys.ALL_EVENTS); // слушаем ВСЕ события
    }

    @Bean
    public Binding dlqBinding(Queue notificationsDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(notificationsDlq)
                .to(deadLetterExchange)
                .with(NOTIFICATIONS_DLQ);
    }
}