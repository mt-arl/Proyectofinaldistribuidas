package com.logiflow.fleet_service.service;

import com.logiflow.fleet_service.dto.mapper.VehiculoMapper;
import com.logiflow.fleet_service.dto.request.*;
import com.logiflow.fleet_service.dto.response.VehiculoResponseDto;
import com.logiflow.fleet_service.enums.EstadoVehiculo;
import com.logiflow.fleet_service.model.*;
import com.logiflow.fleet_service.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FleetService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    @Transactional
    public VehiculoResponseDto crearMotorizado(MotorizadoRequestDto dto) {
        Motorizado entity = vehiculoMapper.toEntity(dto);
        return vehiculoMapper.toResponse(vehiculoRepository.save(entity));
    }

    @Transactional
    public VehiculoResponseDto crearLiviano(LivianoRequestDto dto) {
        VehiculoLiviano entity = vehiculoMapper.toEntity(dto);
        return vehiculoMapper.toResponse(vehiculoRepository.save(entity));
    }

    @Transactional
    public VehiculoResponseDto crearCamion(CamionRequestDto dto) {
        Camion entity = vehiculoMapper.toEntity(dto);
        return vehiculoMapper.toResponse(vehiculoRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponseDto> listarFlota() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Requisito PDF: Actualización de estado
    @Transactional
    public VehiculoResponseDto actualizarEstado(UUID id, EstadoVehiculo nuevoEstado) {
        VehiculoEntrega v = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        v.setEstado(nuevoEstado);
        return vehiculoMapper.toResponse(vehiculoRepository.save(v));
    }
}