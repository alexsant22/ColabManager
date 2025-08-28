package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.FuncionarioCreateRequest;
import com.example.ColabManager.dto.request.FuncionarioUpdateRequest;
import com.example.ColabManager.dto.response.FuncionarioResponse;
import com.example.ColabManager.entity.Cargo;
import com.example.ColabManager.entity.Departamento;
import com.example.ColabManager.entity.Funcionario;
import com.example.ColabManager.entity.enums.StatusFuncionario;
import com.example.ColabManager.repository.CargoRepo;
import com.example.ColabManager.repository.DepartamentoRepo;
import com.example.ColabManager.repository.FuncionarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepo funcionarioRepository;
    private final CargoRepo cargoRepository;
    private final DepartamentoRepo departamentoRepository;

    // GET /funcionarios - Lista todos os funcionários
    @Transactional(readOnly = true)
    public List<FuncionarioResponse> findAll() {
        return funcionarioRepository.findAll().stream()
                .map(FuncionarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /funcionarios/{id} - Retorna detalhes de um funcionário específico
    @Transactional(readOnly = true)
    public FuncionarioResponse findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return FuncionarioResponse.fromEntity(funcionario);
    }

    // POST /funcionarios - Cadastra um novo funcionário
    @Transactional
    public FuncionarioResponse create(FuncionarioCreateRequest request) {
        // Verificar se CPF já existe
        if (funcionarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + request.getCpf());
        }

        // Verificar se email já existe
        if (funcionarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }

        // Converter request para entity
        Funcionario funcionario = request.toEntity();

        // Buscar e validar cargo
        Cargo cargo = cargoRepository.findById(request.getCargo_id())
                .orElseThrow(() -> new RuntimeException("Cargo não encontrado com ID: " + request.getCargo_id()));
        funcionario.setCargo(cargo);

        // Buscar e validar departamento
        Departamento departamento = departamentoRepository.findById(request.getDepartamento_id())
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + request.getDepartamento_id()));
        funcionario.setDepartamento(departamento);

        // Salvar funcionário
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);

        return FuncionarioResponse.fromEntity(savedFuncionario);
    }

    // PUT /funcionarios/{id} - Atualiza os dados de um funcionário
    @Transactional
    public FuncionarioResponse update(Long id, FuncionarioUpdateRequest request) {
        // Buscar funcionário existente
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        // Verificar se novo CPF já existe (se foi alterado)
        if (!funcionario.getCpf().equals(request.getCpf()) &&
                funcionarioRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + request.getCpf());
        }

        // Verificar se novo email já existe (se foi alterado)
        if (!funcionario.getEmail().equals(request.getEmail()) &&
                funcionarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }

        // Aplicar updates nos campos básicos
        request.applyToEntity(funcionario);

        // Atualizar cargo se necessário
        if (!funcionario.getCargo().getId().equals(request.getCargo_id())) {
            Cargo cargo = cargoRepository.findById(request.getCargo_id())
                    .orElseThrow(() -> new RuntimeException("Cargo não encontrado com ID: " + request.getCargo_id()));
            funcionario.setCargo(cargo);
        }

        // Atualizar departamento se necessário
        if (!funcionario.getDepartamento().getId().equals(request.getDepartamento_id())) {
            Departamento departamento = departamentoRepository.findById(request.getDepartamento_id())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado com ID: " + request.getDepartamento_id()));
            funcionario.setDepartamento(departamento);
        }

        // Salvar alterações
        Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);

        return FuncionarioResponse.fromEntity(updatedFuncionario);
    }

    // DELETE /funcionarios/{id} - Remove um funcionário
    @Transactional
    public void delete(Long id) {
        // Verificar se funcionário existe
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        // Verificar se tem usuário vinculado (depende da regra de negócio)
        if (funcionario.getUsuario() != null) {
            throw new RuntimeException("Não é possível excluir funcionário com usuário vinculado");
        }

        // Verificar se tem histórico (opcional - depende da regra)
        if (funcionario.getHistoricos() != null && !funcionario.getHistoricos().isEmpty()) {
            // Pode ser que você queira manter o histórico mesmo excluindo o funcionário
            // Nesse caso, apenas remove o funcionário e mantém o histórico
        }

        // Excluir funcionário
        funcionarioRepository.delete(funcionario);
    }

    // Métodos auxiliares opcionais
    @Transactional(readOnly = true)
    public List<FuncionarioResponse> findByCargo(Long cargoId) {
        return funcionarioRepository.findByCargoId(cargoId).stream()
                .map(FuncionarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> findByDepartamento(Long departamentoId) {
        return funcionarioRepository.findByDepartamentoId(departamentoId).stream()
                .map(FuncionarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> findByStatus(StatusFuncionario status) {
        return funcionarioRepository.findByStatus(status).stream()
                .map(FuncionarioResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
