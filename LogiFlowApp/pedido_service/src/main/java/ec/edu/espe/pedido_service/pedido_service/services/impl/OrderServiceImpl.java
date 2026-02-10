package ec.edu.espe.pedido_service.pedido_service.services.impl;

import ec.edu.espe.pedido_service.pedido_service.dto.OrderRequestDto;
import ec.edu.espe.pedido_service.pedido_service.dto.OrderResponseDto;
import ec.edu.espe.pedido_service.pedido_service.exception.BusinessException;
import ec.edu.espe.pedido_service.pedido_service.exception.ResourceNotFoundException;
import ec.edu.espe.pedido_service.pedido_service.model.DeliveryType;
import ec.edu.espe.pedido_service.pedido_service.model.Order;
import ec.edu.espe.pedido_service.pedido_service.model.OrderStatus;
import ec.edu.espe.pedido_service.pedido_service.producer.PedidoEventProducer;
import ec.edu.espe.pedido_service.pedido_service.repositories.OrderRepository;
import ec.edu.espe.pedido_service.pedido_service.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final PedidoEventProducer eventProducer; // 1. Inyectamos el productor

    // Actualizamos el constructor para la inyección de dependencias
    public OrderServiceImpl(OrderRepository repository, PedidoEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    private void validateDeliveryType(DeliveryType type) {
        if (type == null) {
            throw new BusinessException("Delivery type must be specified");
        }
    }

    @Override
    public OrderResponseDto create(OrderRequestDto request) {
        validateDeliveryType(request.getDeliveryType());

        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .deliveryType(request.getDeliveryType())
                .status(OrderStatus.RECIBIDO)
                .active(true)
                .build();

        OrderResponseDto response = mapToDto(repository.save(order));

        // 2. Notificamos la creación del pedido (Fase 2)
        eventProducer.enviarEventoEstadoActualizado(response);

        return response;
    }

    @Override
    public OrderResponseDto findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToDto(order);
    }

    @Override
    public List<OrderResponseDto> findAll() {
        // Usamos findByActiveTrue para cumplir con la lógica de cancelación lógica
        return repository.findByActiveTrue()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public OrderResponseDto update(Long id, OrderRequestDto request) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setCustomerName(request.getCustomerName());
        order.setOrigin(request.getOrigin());
        order.setDestination(request.getDestination());
        order.setDeliveryType(request.getDeliveryType());

        // Guardamos explícitamente y enviamos el evento
        OrderResponseDto response = mapToDto(repository.save(order));
        eventProducer.enviarEventoEstadoActualizado(response);

        return response;
    }

    @Override
    public void cancel(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getActive()) {
            throw new BusinessException("Order is already canceled");
        }

        order.setStatus(OrderStatus.CANCELADO);
        order.setActive(false);

        // 3. Notificamos la cancelación (Fase 2 - Inicio de Saga si aplica)
        OrderResponseDto response = mapToDto(repository.save(order));
        eventProducer.enviarEventoEstadoActualizado(response);
    }

    // ... (El resto de tus métodos findById, findAll y findByCustomerName se mantienen igual)
    private OrderResponseDto mapToDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .origin(order.getOrigin())
                .destination(order.getDestination())
                .deliveryType(order.getDeliveryType())
                .status(order.getStatus())
                .build();
    }
    @Override
    public List<OrderResponseDto> findByCustomerName(String customerName) {

        List<Order> orders =
                repository.findByCustomerNameContainingIgnoreCase(customerName);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No orders found matching customer name: " + customerName
            );
        }

        return orders.stream()
                .map(this::mapToDto)
                .toList();
    }



}