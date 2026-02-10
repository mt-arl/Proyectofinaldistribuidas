package com.logiflow.fleet_service.repository;

import com.logiflow.fleet_service.model.VehiculoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntrega, UUID> {
    // Aquí puedes agregar métodos personalizados si necesitas, ej:
    // List<VehiculoEntrega> findByEstado(EstadoVehiculo estado);
    boolean existsByPlaca(String placa);
}