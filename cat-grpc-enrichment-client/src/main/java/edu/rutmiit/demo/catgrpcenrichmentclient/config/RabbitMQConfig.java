package edu.rutmiit.demo.catgrpcenrichmentclient.config;

import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ENRICHMENT_QUEUE = "q.enrichment.cat-created";
    public static final String ENRICHMENT_DLQ = "q.enrichment.cat-created.dlq";

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
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(3);
        factory.setDefaultRequeueRejected(false);
        return factory;
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
    public Queue enrichmentQueue() {
        return QueueBuilder.durable(ENRICHMENT_QUEUE)
                .deadLetterExchange(RoutingKeys.EXCHANGE + ".dlx")
                .deadLetterRoutingKey(ENRICHMENT_DLQ)
                .build();
    }

    @Bean
    public Queue enrichmentDlq() {
        return QueueBuilder.durable(ENRICHMENT_DLQ).build();
    }

    @Bean
    public Binding enrichmentBinding(Queue enrichmentQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(enrichmentQueue).to(eventsExchange).with(RoutingKeys.CAT_CREATED);
    }

    @Bean
    public Binding enrichmentDlqBinding(Queue enrichmentDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(enrichmentDlq).to(deadLetterExchange).with(ENRICHMENT_DLQ);
    }
}