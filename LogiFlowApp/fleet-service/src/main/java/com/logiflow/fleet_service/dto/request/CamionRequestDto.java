package com.logiflow.fleet_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CamionRequestDto extends VehiculoRequestDto {

    @NotNull(message = "El número de ejes es obligatorio")
    private Integer numeroEjes;
}