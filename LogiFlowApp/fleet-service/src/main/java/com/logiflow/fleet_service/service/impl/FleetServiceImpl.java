package com.logiflow.fleet_service.service.impl;

import com.logiflow.fleet_service.dto.mapper.VehiculoMapper;
import com.logiflow.fleet_service.dto.request.*;
import com.logiflow.fleet_service.dto.response.VehiculoResponseDto;
import com.logiflow.fleet_service.enums.EstadoVehiculo;
import com.logiflow.fleet_service.model.*;
import com.logiflow.fleet_service.repository.VehiculoRepository;
import com.logiflow.fleet_service.service.IFleetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FleetServiceImpl implements IFleetService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Override
    @Transactional
    public VehiculoResponseDto crearMotorizado(MotorizadoRequestDto request) {
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esa placa");
        }
        Motorizado motorizado = vehiculoMapper.toEntity(request);
        VehiculoEntrega guardado = vehiculoRepository.save(motorizado);
        return vehiculoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public VehiculoResponseDto crearCamion(CamionRequestDto request) {
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esa placa");
        }
        Camion camion = vehiculoMapper.toEntity(request);
        VehiculoEntrega guardado = vehiculoRepository.save(camion);
        return vehiculoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public VehiculoResponseDto crearLiviano(LivianoRequestDto request) {
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo con esa placa");
        }
        VehiculoLiviano liviano = vehiculoMapper.toEntity(request);
        VehiculoEntrega guardado = vehiculoRepository.save(liviano);
        return vehiculoMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> listarFlota() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehiculoResponseDto actualizarEstado(UUID id, EstadoVehiculo nuevoEstado) {
        VehiculoEntrega vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

       // Actualizamos estado (Requisito del PDF: gestión de estados DISPONIBLE, EN RUTA, etc.) [cite: 84]
        vehiculo.setEstado(nuevoEstado);

        VehiculoEntrega actualizado = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toResponse(actualizado);
    }
}