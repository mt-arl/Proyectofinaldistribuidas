package com.logiflow.fleet_service.dto.response;

import com.logiflow.fleet_service.enums.EstadoVehiculo;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class VehiculoResponseDto {
    private UUID id;
    private String placa;
    private String modelo;
    private String conductorNombre;
    private EstadoVehiculo estado;
    private String tipoVehiculo; // "MOTORIZADO", "LIVIANO", "CAMION"
    private Double capacidadCarga;

    // Campos específicos (nulos si no aplican)
    private Integer cilindraje;
    private Integer numeroEjes;
    private Integer numeroPuertas;
}