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
        List<FuncionarioResponse> funcionarios = funcionarioService.findAll();
        return ResponseEntity.ok(funcionarios);
    }

    // GET /funcionarios/{id} - Retorna detalhes de um funcionário específico
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> getFuncionarioById(@PathVariable Long id) {
        FuncionarioResponse funcionario = funcionarioService.findById(id);
        return ResponseEntity.ok(funcionario);
    }

    // GET /funcionarios/cpf/{cpf} - Retorna funcionário por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<FuncionarioResponse> getFuncionarioByCpf(@PathVariable String cpf) {
        FuncionarioResponse funcionario = funcionarioService.findByCpf(cpf);
        return ResponseEntity.ok(funcionario);
    }

    // GET /funcionarios/status/{status} - Busca funcionários por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByStatus(
            @PathVariable StatusFuncionario status) {
        List<FuncionarioResponse> funcionarios = funcionarioService.findByStatus(status);
        return ResponseEntity.ok(funcionarios);
    }

    // GET /funcionarios/cargo/{cargoId} - Busca funcionários por cargo
    @GetMapping("/cargo/{cargoId}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByCargo(@PathVariable Long cargoId) {
        List<FuncionarioResponse> funcionarios = funcionarioService.findByCargo(cargoId);
        return ResponseEntity.ok(funcionarios);
    }

    // GET /funcionarios/departamento/{departamentoId} - Busca funcionários por departamento
    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<FuncionarioResponse>> getFuncionariosByDepartamento(@PathVariable Long departamentoId) {
        List<FuncionarioResponse> funcionarios = funcionarioService.findByDepartamento(departamentoId);
        return ResponseEntity.ok(funcionarios);
    }

    // POST /funcionarios - Cadastra um novo funcionário
    @PostMapping
    public ResponseEntity<FuncionarioResponse> createFuncionario(
            @Valid @RequestBody FuncionarioCreateRequest request) {
        FuncionarioResponse novoFuncionario = funcionarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    // PUT /funcionarios/{id} - Atualiza os dados de um funcionário
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> updateFuncionario(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioUpdateRequest request) {
        FuncionarioResponse funcionarioAtualizado = funcionarioService.update(id, request);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // DELETE /funcionarios/{id} - Remove um funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Exception Handler para tratar as RuntimeExceptions do Service
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}