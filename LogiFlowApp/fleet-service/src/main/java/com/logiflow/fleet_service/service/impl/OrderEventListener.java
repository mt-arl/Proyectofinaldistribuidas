package com.logiflow.fleet_service.service.impl;

import com.logiflow.fleet_service.enums.EstadoVehiculo;
import com.logiflow.fleet_service.model.VehiculoEntrega;
import com.logiflow.fleet_service.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderEventListener {

    private final VehiculoRepository vehiculoRepository;

    @RabbitListener(queues = "logiflow.fleet.queue")
    public void handleOrderEvent(Map<String, Object> pedido) {
        System.out.println(">>> Fase 2: Integración detectada para pedido ID: " + pedido.get("id"));

        // Buscamos el primer vehículo disponible
        Optional<VehiculoEntrega> vehiculoOpt = vehiculoRepository.findAll().stream()
                .filter(v -> v.getEstado() == EstadoVehiculo.DISPONIBLE)
                .findFirst();

        if (vehiculoOpt.isPresent()) {
            VehiculoEntrega v = vehiculoOpt.get();
            v.setEstado(EstadoVehiculo.EN_RUTA);
            vehiculoRepository.save(v);
            System.out.println(">>> Vehículo " + v.getPlaca() + " asignado y puesto EN RUTA.");
        } else {
            System.out.println(">>> ALERTA: No hay vehículos disponibles para el pedido.");
        }
    }
}