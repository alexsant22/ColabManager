package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.DepartamentoRequest;
import com.example.ColabManager.dto.response.DepartamentoResponse;
import com.example.ColabManager.entity.Departamento;
import com.example.ColabManager.exception.BusinessException;
import com.example.ColabManager.exception.ResourceNotFoundException;
import com.example.ColabManager.repository.DepartamentoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        return DepartamentoResponse.fromEntity(departamento);
    }

    @Transactional
    // POST /departamentos - Cadastra um novo departamento
    public DepartamentoResponse create(DepartamentoRequest request) {

        // Verificar se o nome do departamento já existe
        if (repository.existsByNome(request.getNome())) {
            throw new BusinessException("Nome do departamento já cadastrado: " + request.getNome());
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        // Verificar se está tentando alterar o nome para um já existente
        if (!existingDepartamento.getNome().equals(request.getNome())
                && repository.existsByNome(request.getNome())) {
            throw new BusinessException("Nome do departamento já cadastrado: " + request.getNome());
        }

        // Aplicar as atualizações
        request.applyToEntity(existingDepartamento);

        // Salvar as alterações
        Departamento updatedDepartamento = repository.save(existingDepartamento);

        return DepartamentoResponse.fromEntity(updatedDepartamento);
    }

    @Transactional
    // DELETE /departamentos/{id} - Remove um departamento existente
    public void delete(Long id) {

        // Verificar se o departamento existe
        Departamento departamento = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

        // Verificar se o departamento está associado a algum funcionário
        if (departamento.getFuncionarios() != null
                && !departamento.getFuncionarios().isEmpty()) {
            throw new BusinessException(
                    "Não é possível deletar o departamento, pois está associado a funcionários."
            );
        }

        // Deletar o departamento
        repository.delete(departamento);
    }
}
