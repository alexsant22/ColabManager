package com.example.ColabManager.controller;

import com.example.ColabManager.dto.request.CargoRequest;
import com.example.ColabManager.dto.response.CargoResponse;
import com.example.ColabManager.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    // GET /cargos - Lista todos os cargos
    @GetMapping
    public ResponseEntity<List<CargoResponse>> getAllCargos() {
        List<CargoResponse> cargos = cargoService.findAll();
        return ResponseEntity.ok(cargos);
    }

    // GET /cargos/{id} - Retorna detalhes de um cargo específico
    @GetMapping("/{id}")
    public ResponseEntity<CargoResponse> getCargoById(@PathVariable Long id) {
        CargoResponse cargo = cargoService.findById(id);
        return ResponseEntity.ok(cargo);
    }

    // POST /cargos - Cadastra um novo cargo
    @PostMapping
    public ResponseEntity<CargoResponse> createCargo(
            @Valid @RequestBody CargoRequest request) {
        CargoResponse createdCargo = cargoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCargo);
    }

    // PUT /cargos/{id} - Atualiza um cargo existente
    @PutMapping("/{id}")
    public ResponseEntity<CargoResponse> updateCargo(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequest request) {
        CargoResponse updatedCargo = cargoService.update(id, request);
        return ResponseEntity.ok(updatedCargo);
    }

    // DELETE /cargos/{id} - Deleta um cargo existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        cargoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
