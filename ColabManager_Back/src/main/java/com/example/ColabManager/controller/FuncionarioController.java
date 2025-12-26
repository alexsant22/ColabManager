package com.example.ColabManager.controller;

import com.example.ColabManager.dto.request.FuncionarioCreateRequest;
import com.example.ColabManager.dto.request.FuncionarioUpdateRequest;
import com.example.ColabManager.dto.response.FuncionarioResponse;
import com.example.ColabManager.entity.enums.StatusFuncionario;
import com.example.ColabManager.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    // GET /funcionarios - Lista todos os funcionários
    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> getAllFuncionarios() {
        return ResponseEntity.ok(funcionarioService.findAll());
    }

    // GET /funcionarios/{id} - Retorna detalhes de um funcionário específico
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> getFuncionarioById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    // GET /funcionarios/cpf/{cpf} - Retorna detalhes de um funcionário específico pelo CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<FuncionarioResponse> getFuncionarioByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(funcionarioService.findByCpf(cpf));
    }

    // GET /funcionarios/status/{status} - Retorna funcionários por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByStatus(
            @PathVariable StatusFuncionario status) {
        return ResponseEntity.ok(funcionarioService.findByStatus(status));
    }

    // GET /funcionarios/cargo/{cargoId} - Retorna funcionários por cargo
    @GetMapping("/cargo/{cargoId}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByCargo(@PathVariable Long cargoId) {
        return ResponseEntity.ok(funcionarioService.findByCargo(cargoId));
    }

    // GET /funcionarios/departamento/{departamentoId} - Retorna funcionários por departamento
    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByDepartamento(
            @PathVariable Long departamentoId) {
        return ResponseEntity.ok(funcionarioService.findByDepartamento(departamentoId));
    }

    // POST /funcionarios - Cadastra um novo funcionário
    @PostMapping
    public ResponseEntity<FuncionarioResponse> createFuncionario(
            @Valid @RequestBody FuncionarioCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(funcionarioService.create(request));
    }

    // PUT /funcionarios/{id} - Atualiza os dados de um funcionário existente
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> updateFuncionario(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioUpdateRequest request) {
        return ResponseEntity.ok(funcionarioService.update(id, request));
    }

    // DELETE /funcionarios/{id} - Remove um funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}