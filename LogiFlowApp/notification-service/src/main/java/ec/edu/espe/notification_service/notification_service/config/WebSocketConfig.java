package ec.edu.espe.notification_service.notification_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker simple para enviar mensajes a los clientes
        // Los clientes se suscribirán a rutas que empiecen con /topic [cite: 128]
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint de conexión para el frontend (Fase 3) [cite: 127]
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*"); // En producción, restringir a tu dominio
    }
}