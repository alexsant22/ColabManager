package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.FuncionarioCreateRequest;
import com.example.ColabManager.dto.request.FuncionarioUpdateRequest;
import com.example.ColabManager.dto.response.FuncionarioResponse;
import com.example.ColabManager.entity.Cargo;
import com.example.ColabManager.entity.Departamento;
import com.example.ColabManager.entity.Funcionario;
import com.example.ColabManager.entity.HistoricoFuncionario;
import com.example.ColabManager.entity.enums.StatusFuncionario;
import com.example.ColabManager.exception.BusinessException;
import com.example.ColabManager.exception.ResourceNotFoundException;
import com.example.ColabManager.repository.CargoRepo;
import com.example.ColabManager.repository.DepartamentoRepo;
import com.example.ColabManager.repository.FuncionarioRepo;
import com.example.ColabManager.repository.HistoricoFuncionarioRepo;
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
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));
        return FuncionarioResponse.fromEntity(funcionario);
    }

    // GET /funcionarios/{cpf} - Retorna detalhes de um funcionário específico
    @Transactional(readOnly = true)
    public FuncionarioResponse findByCpf(String cpf)  {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com CPF: " + cpf));
        return FuncionarioResponse.fromEntity(funcionario);
    }

    // POST /funcionarios - Cadastra um novo funcionário
    @Transactional
    public FuncionarioResponse create(FuncionarioCreateRequest request) {
        // Verificar se CPF já existe
        if (funcionarioRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + request.getCpf());
        }

        // Verificar se email já existe
        if (funcionarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + request.getEmail());
        }

        // Converter request para entity
        Funcionario funcionario = request.toEntity();

        // Buscar e validar cargo
        Cargo cargo = cargoRepository.findById(request.getCargo_id())
                .orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com ID: " + request.getCargo_id()));
        funcionario.setCargo(cargo);

        // Buscar e validar departamento
        Departamento departamento = departamentoRepository.findById(request.getDepartamento_id())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + request.getDepartamento_id()));
        funcionario.setDepartamento(departamento);

        // Salvar funcionário
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);

        return FuncionarioResponse.fromEntity(savedFuncionario);
    }

    // PUT /funcionarios/{id} - Atualiza os dados de um funcionário
    @Transactional
    public FuncionarioResponse update(Long id, FuncionarioUpdateRequest request) {

        // Buscar funcionário
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado com ID: " + id)
                );

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

        // Capturar valores antigos (ANTES do update)
        BigDecimal salarioAnterior = funcionario.getSalario();
        String cargoAnterior = funcionario.getCargo().getNome();
        String departamentoAnterior = funcionario.getDepartamento().getNome();

        Long cargoIdAnterior = funcionario.getCargo().getId();
        Long departamentoIdAnterior = funcionario.getDepartamento().getId();

        // Atualizar campos básicos
        request.applyToEntity(funcionario);

        // ===== HISTÓRICO: SALÁRIO =====
        if (salarioAnterior.compareTo(funcionario.getSalario()) != 0) {
            registrarHistorico(
                    funcionario,
                    "SALARIO",
                    "Salário alterado de R$ " + salarioAnterior +
                            " para R$ " + funcionario.getSalario()
            );
        }

        // ===== HISTÓRICO: CARGO =====
        if (!cargoIdAnterior.equals(request.getCargo_id())) {

            Cargo novoCargo = cargoRepository.findById(request.getCargo_id())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Cargo não encontrado com ID: " + request.getCargo_id())
                    );

            funcionario.setCargo(novoCargo);

            registrarHistorico(
                    funcionario,
                    "CARGO",
                    "Cargo alterado de '" + cargoAnterior +
                            "' para '" + novoCargo.getNome() + "'"
            );
        }

        // ===== HISTÓRICO: DEPARTAMENTO =====
        if (!departamentoIdAnterior.equals(request.getDepartamento_id())) {

            Departamento novoDepartamento = departamentoRepository.findById(request.getDepartamento_id())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Departamento não encontrado com ID: " + request.getDepartamento_id())
                    );

            funcionario.setDepartamento(novoDepartamento);

            registrarHistorico(
                    funcionario,
                    "DEPARTAMENTO",
                    "Departamento alterado de '" + departamentoAnterior +
                            "' para '" + novoDepartamento.getNome() + "'"
            );
        }

        // Salvar funcionário atualizado
        Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);

        return FuncionarioResponse.fromEntity(updatedFuncionario);
    }

    // DELETE /funcionarios/{id} - Remove um funcionário
    @Transactional
    public void delete(Long id) {
        // Verificar se funcionário existe
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com ID: " + id));

        // Verificar se tem usuário vinculado (depende da regra de negócio)
        if (funcionario.getUsuario() != null) {
            throw new BusinessException("Não é possível excluir funcionário com usuário vinculado");
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

    // Registra uma entrada no histórico do funcionário
    private void registrarHistorico(
            Funcionario funcionario,
            String tipo,
            String descricao
    ) {
        HistoricoFuncionario historico = new HistoricoFuncionario();
        historico.setFuncionario(funcionario);
        historico.setTipo(tipo);
        historico.setDescricao(descricao);

        historicoFuncionarioRepository.save(historico);
    }


}
