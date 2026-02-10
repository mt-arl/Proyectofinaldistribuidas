package ec.edu.espe.pedido_service.pedido_service.producer;

import ec.edu.espe.pedido_service.pedido_service.config.RabbitMQConfig;
import ec.edu.espe.pedido_service.pedido_service.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarEventoEstadoActualizado(OrderResponseDto pedido) {
        // En la Fase 2, enviamos el DTO completo para que el NotificationService tenga toda la info
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                pedido
        );
        System.out.println("Evento enviado a RabbitMQ para el pedido ID: " + pedido.getId());
    }
}