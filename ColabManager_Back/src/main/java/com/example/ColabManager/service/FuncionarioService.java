package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.FuncionarioCreateRequest;
import com.example.ColabManager.dto.request.FuncionarioUpdateRequest;
import com.example.ColabManager.dto.response.FuncionarioResponse;
import com.example.ColabManager.entity.*;
import com.example.ColabManager.entity.enums.StatusFuncionario;
import com.example.ColabManager.entity.enums.TipoHistorico;
import com.example.ColabManager.exception.BusinessException;
import com.example.ColabManager.exception.ResourceNotFoundException;
import com.example.ColabManager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepo funcionarioRepository;
    private final CargoRepo cargoRepository;
    private final DepartamentoRepo departamentoRepository;
    private final HistoricoFuncionarioRepo historicoFuncionarioRepository;

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));
        return FuncionarioResponse.fromEntity(funcionario);
    }

    // GET /funcionarios/cpf/{cpf}
    @Transactional(readOnly = true)
    public FuncionarioResponse findByCpf(String cpf) {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado com CPF: " + cpf));
        return FuncionarioResponse.fromEntity(funcionario);
    }

    // GET /funcionarios/{id}/historico
    @Transactional(readOnly = true)
    public List<HistoricoFuncionario> getHistorico(Long funcionarioId) {

        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new ResourceNotFoundException(
                    "Funcionário não encontrado com ID: " + funcionarioId
            );
        }

        return historicoFuncionarioRepository
                .findByFuncionarioIdOrderByAlteradoEmDesc(funcionarioId);
    }

    // POST /funcionarios
    @Transactional
    public FuncionarioResponse create(FuncionarioCreateRequest request) {

        // Verificar CPF
        if (funcionarioRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
        }

        // Verificar Email
        if (funcionarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        }

        // Converter request para entity
        Funcionario funcionario = request.toEntity();

        // Cargo
        Cargo cargo = cargoRepository.findById(request.getCargo_id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cargo não encontrado com ID: " + request.getCargo_id()));
        funcionario.setCargo(cargo);

        // Departamento
        Departamento departamento = departamentoRepository.findById(request.getDepartamento_id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Departamento não encontrado com ID: " + request.getDepartamento_id()));
        funcionario.setDepartamento(departamento);

        // Salvar funcionário
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);

        // ===== HISTÓRICO: CRIAÇÃO =====
        registrarHistorico(
                savedFuncionario,
                TipoHistorico.CRIACAO,
                "Funcionário cadastrado no sistema"
        );

        return FuncionarioResponse.fromEntity(savedFuncionario);
    }

    // PUT /funcionarios/{id}
    @Transactional
    public FuncionarioResponse update(Long id, FuncionarioUpdateRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));

        // Validar CPF
        if (!funcionario.getCpf().equals(request.getCpf()) &&
                funcionarioRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
        }

        // Validar Email
        if (!funcionario.getEmail().equals(request.getEmail()) &&
                funcionarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        }

        // Valores anteriores
        BigDecimal salarioAnterior = funcionario.getSalario();

        String cargoAnterior = funcionario.getCargo() != null
                ? funcionario.getCargo().getNome()
                : "N/A";

        String departamentoAnterior = funcionario.getDepartamento() != null
                ? funcionario.getDepartamento().getNome()
                : "N/A";

        Long cargoIdAnterior = funcionario.getCargo().getId();
        Long departamentoIdAnterior = funcionario.getDepartamento().getId();

        // ===== HISTÓRICO: SALÁRIO =====
        if (salarioAnterior.compareTo(request.getSalario()) != 0) {
            registrarHistorico(
                    funcionario,
                    TipoHistorico.SALARIO,
                    "Salário alterado de R$ " + salarioAnterior +
                            " para R$ " + request.getSalario()
            );
        }

        // ===== HISTÓRICO: CARGO =====
        if (!cargoIdAnterior.equals(request.getCargo_id())) {

            Cargo novoCargo = cargoRepository.findById(request.getCargo_id())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Cargo não encontrado com ID: " + request.getCargo_id()));

            registrarHistorico(
                    funcionario,
                    TipoHistorico.CARGO,
                    "Cargo alterado de '" + cargoAnterior +
                            "' para '" + novoCargo.getNome() + "'"
            );

            funcionario.setCargo(novoCargo);
        }

        // ===== HISTÓRICO: DEPARTAMENTO =====
        if (!departamentoIdAnterior.equals(request.getDepartamento_id())) {

            Departamento novoDepartamento = departamentoRepository.findById(request.getDepartamento_id())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Departamento não encontrado com ID: " + request.getDepartamento_id()));

            registrarHistorico(
                    funcionario,
                    TipoHistorico.DEPARTAMENTO,
                    "Departamento alterado de '" + departamentoAnterior +
                            "' para '" + novoDepartamento.getNome() + "'"
            );

            funcionario.setDepartamento(novoDepartamento);
        }

        // Atualizar dados básicos
        request.applyToEntity(funcionario);

        // Garantir atualização explícita do salário
        funcionario.setSalario(request.getSalario());

        Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);

        return FuncionarioResponse.fromEntity(updatedFuncionario);
    }

    // DELETE /funcionarios/{id}
    @Transactional
    public void delete(Long id) {

        // Verificar se funcionário existe
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));

        // Verificar se já está inativo
        if (funcionario.getStatus() == StatusFuncionario.INATIVO) {
            throw new BusinessException("Funcionário já está inativo");
        }

        // Regra de negócio: não permitir inativar funcionário demitido
        if (funcionario.getStatus() == StatusFuncionario.DEMITIDO) {
            throw new BusinessException("Funcionário demitido não pode ser inativado");
        }

        // Verificar se tem usuário vinculado
        if (funcionario.getUsuario() != null) {
            throw new BusinessException("Não é possível inativar funcionário com usuário vinculado");
        }

        // Soft delete
        funcionario.setStatus(StatusFuncionario.INATIVO);

        // Histórico
        registrarHistorico(
                funcionario,
                TipoHistorico.INATIVACAO,
                "Funcionário inativado no sistema"
        );

        funcionarioRepository.save(funcionario);
    }

    // ===== CONSULTAS AUXILIARES =====

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

    // ===== REGISTRO DE HISTÓRICO =====
    private void registrarHistorico(
            Funcionario funcionario,
            TipoHistorico tipo,
            String descricao
    ) {
        HistoricoFuncionario historico = new HistoricoFuncionario();
        historico.setFuncionario(funcionario);
        historico.setTipo(tipo);
        historico.setDescricao(descricao);

        historicoFuncionarioRepository.save(historico);
    }
}
