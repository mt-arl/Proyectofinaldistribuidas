package ec.edu.espe.pedido_service.pedido_service.config;

import com.fasterxml.jackson.databind.ObjectMapper; // ESTA ES LA IMPORTACIÓN QUE TE FALTABA
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "logiflow.pedidos.exchange";
    public static final String ROUTING_KEY = "pedido.estado.actualizado";

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Cambia este método en RabbitMQConfig.java

    @Bean
    public MessageConverter jsonMessageConverter() {
        // Instanciarlo manualmente evita el error de "bean no encontrado" en el arranque
        return new Jackson2JsonMessageConverter(new com.fasterxml.jackson.databind.ObjectMapper());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}