package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.DepartamentoRequest;
import com.example.ColabManager.dto.response.DepartamentoResponse;
import com.example.ColabManager.entity.Departamento;
import com.example.ColabManager.repository.DepartamentoRepo;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepo repository;

    @Transactional(readOnly = true)
    // GET /departamentos - Lista todos os departamentos
    public List<DepartamentoResponse> findAll() {
        return repository.findAll().stream()
                .map(DepartamentoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    // GET /departamentos/{id} - Retorna detalhes de um departamento específico
    public DepartamentoResponse findById(Long id) {
        Departamento departamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));
        return DepartamentoResponse.fromEntity(departamento);
    }

    @Transactional
    // POST /departamentos - Cadastra um novo departamento
    public DepartamentoResponse create(DepartamentoRequest request) {
        // Verificar se o nome do departamento já existe
        if (repository.existsByNome(request.getNome())) {
            throw new RuntimeException("Nome do departamento já cadastrado: " + request.getNome());
        }

        // Converter request para entity
        Departamento departamento = request.toEntity();

        // Salvar departamento
        Departamento savedDepartamento = repository.save(departamento);

        return DepartamentoResponse.fromEntity(savedDepartamento);
    }

    @Transactional
    // PUT /departamentos/{id} - Atualiza um departamento existente
    public DepartamentoResponse update(Long id, DepartamentoRequest request) {
        // Buscar o departamento existente
        Departamento existingDepartamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + id));

        // Aplicar as atualizações
        existingDepartamento.setNome(request.getNome());

        // Salvar as alterações
        Departamento updatedDepartamento = repository.save(existingDepartamento);

        return DepartamentoResponse.fromEntity(updatedDepartamento);
    }

    @Transactional
    // DELETE /departamentos/{id} - Remove um departamento existente
    public void delete(Long id) {
        // Verificar se o departamento existe
        if (!repository.existsById(id)) {
            throw new RuntimeException("Departamento não encontrado com ID: " + id);
        }

        // Deletar o departamento
        repository.deleteById(id);
    }
}
