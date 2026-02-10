package ec.edu.espe.pedido_service.pedido_service.controller;

import ec.edu.espe.pedido_service.pedido_service.dto.OrderResponseDto;
import ec.edu.espe.pedido_service.pedido_service.dto.PedidoFiltro;
import ec.edu.espe.pedido_service.pedido_service.dto.RepartidorDto;
import ec.edu.espe.pedido_service.pedido_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderGraphQLController {

    private final OrderService orderService;

    // Coincide con: pedido(id: ID!): Pedido en el schema
    @QueryMapping
    public OrderResponseDto pedido(@Argument Long id) {
        return orderService.findById(id);
    }

    // Coincide con: pedidos(filtro: PedidoFiltro): [Pedido!]!
    @QueryMapping
    public List<OrderResponseDto> pedidos(@Argument PedidoFiltro filtro) {
        // Por ahora devolvemos todos, luego puedes implementar la lógica de filtrado en el Service
        return orderService.findAll();
    }

    @SchemaMapping(typeName = "Pedido", field = "repartidor")
    public RepartidorDto getRepartidor(OrderResponseDto pedido) {
        // En la Fase 2, esto debería llamar al FleetService.
        // Por ahora, simulamos la respuesta si existe un ID.
        if (pedido.getRepartidorId() != null) {
            return new RepartidorDto(pedido.getRepartidorId(), "Repartidor Asignado #" + pedido.getRepartidorId());
        }
        return null;
    }
}