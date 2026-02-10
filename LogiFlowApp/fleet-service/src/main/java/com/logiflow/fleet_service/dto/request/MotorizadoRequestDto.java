package com.logiflow.fleet_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MotorizadoRequestDto extends VehiculoRequestDto {

    @NotNull(message = "El cilindraje es obligatorio")
    private Integer cilindraje;
}