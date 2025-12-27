package com.example.ColabManager.controller;

import com.example.ColabManager.dto.request.DepartamentoRequest;
import com.example.ColabManager.dto.response.DepartamentoResponse;
import com.example.ColabManager.service.DepartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    // GET /departamentos - Lista todos os departamentos
    @GetMapping
    public ResponseEntity<List<DepartamentoResponse>> getAllDepartamentos() {
        List<DepartamentoResponse> departamentos = departamentoService.findAll();
        return ResponseEntity.ok(departamentos);
    }

    // GET /departamentos/{id} - Retorna detalhes de um departamento específico
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> getDepartamentoById(@PathVariable Long id) {
        DepartamentoResponse departamento = departamentoService.findById(id);
        return ResponseEntity.ok(departamento);
    }

    // POST /departamentos - Cadastra um novo departamento
    @PostMapping
    public ResponseEntity<DepartamentoResponse> createDepartamento(
            @Valid @RequestBody DepartamentoRequest request) {
        DepartamentoResponse createdDepartamento = departamentoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartamento);
    }

    // PUT /departamentos/{id} - Atualiza um departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> updateDepartamento(
            @PathVariable Long id,
            @Valid @RequestBody DepartamentoRequest request) {
        DepartamentoResponse updatedDepartamento = departamentoService.update(id, request);
        return ResponseEntity.ok(updatedDepartamento);
    }

    // DELETE /departamentos/{id} - Deleta um departamento existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        departamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
