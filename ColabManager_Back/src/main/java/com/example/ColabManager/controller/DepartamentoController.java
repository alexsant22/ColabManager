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
@RequestMapping("/api/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    // GET /api/departamentos - Lista todos os departamentos
    @GetMapping
    public ResponseEntity<List<DepartamentoResponse>> getAllDepartamentos() {
        List<DepartamentoResponse> departamentos = departamentoService.findAll();
        return ResponseEntity.ok(departamentos);
    }

    // GET /api/departamentos/{id} - Retorna detalhes de um departamento específico
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> getDepartamentoById(@PathVariable Long id) {
        DepartamentoResponse departamento = departamentoService.findById(id);
        return ResponseEntity.ok(departamento);
    }

    // POST /api/departamentos - Cadastra um novo departamento
    @PostMapping
    public ResponseEntity<DepartamentoResponse> createDepartamento(
            @Valid @RequestBody DepartamentoRequest request) {
        DepartamentoResponse createdDepartamento = departamentoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartamento);
    }

    // PUT /api/departamentos/{id} - Atualiza um departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> updateDepartamento(@PathVariable Long id, @RequestBody DepartamentoRequest request) {
        DepartamentoResponse updatedDepartamento = departamentoService.update(id, request);
        return ResponseEntity.ok(updatedDepartamento);
    }

    // DELETE /api/departamentos/{id} - Deleta um departamento existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        departamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Exception Handler para RuntimeException no service
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
