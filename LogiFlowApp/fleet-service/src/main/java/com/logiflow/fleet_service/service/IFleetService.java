package com.logiflow.fleet_service.service;

import com.logiflow.fleet_service.dto.request.CamionRequestDto;
import com.logiflow.fleet_service.dto.request.LivianoRequestDto;
import com.logiflow.fleet_service.dto.request.MotorizadoRequestDto;
import com.logiflow.fleet_service.dto.response.VehiculoResponseDto;
import com.logiflow.fleet_service.enums.EstadoVehiculo;

import java.util.List;
import java.util.UUID;

public interface IFleetService {
    VehiculoResponseDto crearMotorizado(MotorizadoRequestDto request);
    VehiculoResponseDto crearCamion(CamionRequestDto request);
    VehiculoResponseDto crearLiviano(LivianoRequestDto request);

    List<VehiculoResponseDto> listarFlota();

    VehiculoResponseDto actualizarEstado(UUID id, EstadoVehiculo nuevoEstado);
}