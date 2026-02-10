package com.logiflow.fleet_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CAMION")
public class Camion extends VehiculoEntrega {
    private Integer numeroEjes;
}