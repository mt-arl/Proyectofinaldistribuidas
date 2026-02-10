package com.logiflow.fleet_service.dto.mapper;

import com.logiflow.fleet_service.dto.request.*;
import com.logiflow.fleet_service.dto.response.VehiculoResponseDto;
import com.logiflow.fleet_service.enums.EstadoVehiculo;
import com.logiflow.fleet_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    // --- MAPPERS TO ENTITY ---

    public Motorizado toEntity(MotorizadoRequestDto dto) {
        Motorizado v = new Motorizado();
        mapCommon(v, dto);
        v.setCilindraje(dto.getCilindraje());
        return v;
    }

    public VehiculoLiviano toEntity(LivianoRequestDto dto) {
        VehiculoLiviano v = new VehiculoLiviano();
        mapCommon(v, dto);
        v.setNumeroPuertas(dto.getNumeroPuertas());
        return v;
    }

    public Camion toEntity(CamionRequestDto dto) {
        Camion v = new Camion();
        mapCommon(v, dto);
        v.setNumeroEjes(dto.getNumeroEjes());
        return v;
    }

    // Método auxiliar para no repetir código
    private void mapCommon(VehiculoEntrega v, VehiculoRequestDto dto) {
        v.setPlaca(dto.getPlaca());
        v.setModelo(dto.getModelo());
        v.setConductorNombre(dto.getConductorNombre());
        v.setCapacidadCarga(dto.getCapacidadCarga());
        v.setEstado(EstadoVehiculo.DISPONIBLE); // Estado inicial por defecto
    }

    // --- MAPPER TO RESPONSE (Polimórfico) ---

    public VehiculoResponseDto toResponse(VehiculoEntrega v) {
        if (v == null) return null;

        VehiculoResponseDto.VehiculoResponseDtoBuilder builder = VehiculoResponseDto.builder()
                .id(v.getId())
                .placa(v.getPlaca())
                .modelo(v.getModelo())
                .conductorNombre(v.getConductorNombre())
                .estado(v.getEstado())
                .capacidadCarga(v.getCapacidadCarga());

        if (v instanceof Motorizado) {
            builder.tipoVehiculo("MOTORIZADO");
            builder.cilindraje(((Motorizado) v).getCilindraje());
        } else if (v instanceof VehiculoLiviano) {
            builder.tipoVehiculo("LIVIANO");
            builder.numeroPuertas(((VehiculoLiviano) v).getNumeroPuertas());
        } else if (v instanceof Camion) {
            builder.tipoVehiculo("CAMION");
            builder.numeroEjes(((Camion) v).getNumeroEjes());
        }

        return builder.build();
    }
}