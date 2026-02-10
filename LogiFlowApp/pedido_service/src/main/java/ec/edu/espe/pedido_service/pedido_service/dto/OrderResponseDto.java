package ec.edu.espe.pedido_service.pedido_service.dto;

import ec.edu.espe.pedido_service.pedido_service.model.DeliveryType;
import ec.edu.espe.pedido_service.pedido_service.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String customerName;
    private String origin;
    private String destination;
    private DeliveryType deliveryType;
    private OrderStatus status;
    // ... otras importaciones

        // Añadimos el ID del repartidor asignado para que el DataLoader pueda buscarlo
        private Long repartidorId;
        // Este objeto se llenará mediante GraphQL, no por REST necesariamente
        private RepartidorDto repartidor;

}
