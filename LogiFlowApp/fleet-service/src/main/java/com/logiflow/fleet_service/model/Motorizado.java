package com.logiflow.fleet_service.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MOTORIZADO")
public class Motorizado extends VehiculoEntrega {
    private Integer cilindraje;
}