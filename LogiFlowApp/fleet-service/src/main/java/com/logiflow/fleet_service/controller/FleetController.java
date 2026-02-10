package com.logiflow.fleet_service.controller;

import com.logiflow.fleet_service.dto.request.CamionRequestDto;
import com.logiflow.fleet_service.dto.request.LivianoRequestDto;
import com.logiflow.fleet_service.dto.request.MotorizadoRequestDto;
import com.logiflow.fleet_service.dto.response.VehiculoResponseDto;
import com.logiflow.fleet_service.enums.EstadoVehiculo;
import com.logiflow.fleet_service.service.IFleetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flota")
@RequiredArgsConstructor
public class FleetController {

    private final IFleetService fleetService;

    // Endpoint para Motos
    @PostMapping("/motorizados")
    public ResponseEntity<VehiculoResponseDto> crearMotorizado(@Valid @RequestBody MotorizadoRequestDto request) {
        return ResponseEntity.ok(fleetService.crearMotorizado(request));
    }

    // Endpoint para Camiones
    @PostMapping("/camiones")
    public ResponseEntity<VehiculoResponseDto> crearCamion(@Valid @RequestBody CamionRequestDto request) {
        return ResponseEntity.ok(fleetService.crearCamion(request));
    }

    // Endpoint para Autos/Furgonetas livianas
    @PostMapping("/livianos")
    public ResponseEntity<VehiculoResponseDto> crearLiviano(@Valid @RequestBody LivianoRequestDto request) {
        return ResponseEntity.ok(fleetService.crearLiviano(request));
    }

    // Listar todos (Polimórfico)
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDto>> listarFlota() {
        return ResponseEntity.ok(fleetService.listarFlota());
    }

    // Actualizar Estado (Requisito PDF: DISPONIBLE, EN RUTA, etc.)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<VehiculoResponseDto> actualizarEstado(
            @PathVariable UUID id,
            @RequestParam EstadoVehiculo estado) {
        return ResponseEntity.ok(fleetService.actualizarEstado(id, estado));
    }

    // Manejo básico de excepciones
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}