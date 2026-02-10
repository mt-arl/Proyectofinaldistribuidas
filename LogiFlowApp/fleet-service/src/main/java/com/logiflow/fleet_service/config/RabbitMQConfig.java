package com.logiflow.fleet_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "logiflow.fleet.queue";
    public static final String EXCHANGE_NAME = "logiflow.pedidos.exchange";
    public static final String ROUTING_KEY = "pedido.estado.actualizado";

    @Bean
    public Queue fleetQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue fleetQueue, TopicExchange pedidosExchange) {
        return BindingBuilder.bind(fleetQueue).to(pedidosExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}