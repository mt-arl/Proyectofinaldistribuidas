package com.logiflow.fleet_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehiculoRequestDto {
    @NotBlank(message = "La placa es obligatoria")
    private String placa;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @NotBlank(message = "El conductor es obligatorio")
    private String conductorNombre;
    @NotNull(message = "La capacidad es obligatoria")
    private Double capacidadCarga;
}