package ec.edu.espe.notification_service.notification_service.consumer;

import ec.edu.espe.notification_service.notification_service.dto.OrderResponseDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "logiflow.pedidos.queue")
    public void handleOrderEvent(OrderResponseDto pedido) {
        // 1. Log para evidencia de Phase 2 [cite: 191]
        System.out.println(">>> EVENTO RECIBIDO: " + pedido.getId() + " - Estado: " + pedido.getStatus());

        // 2. BROADCAST: Enviar a todos los interesados en este pedido específico
        // El cliente (Frontend) estará escuchando en: /topic/pedido/1
        String topic = "/topic/pedido/" + pedido.getId();
        messagingTemplate.convertAndSend(topic, pedido);

        // 3. BROADCAST GLOBAL: Para el dashboard del supervisor [cite: 62, 105]
        messagingTemplate.convertAndSend("/topic/pedidos", pedido);

        System.out.println(">>> Notificación WebSocket enviada al tópico: " + topic);
    }
}