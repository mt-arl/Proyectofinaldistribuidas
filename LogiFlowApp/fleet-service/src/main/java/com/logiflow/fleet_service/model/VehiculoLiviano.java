package com.logiflow.fleet_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("LIVIANO")
public class VehiculoLiviano extends VehiculoEntrega {
    private Integer numeroPuertas;
}