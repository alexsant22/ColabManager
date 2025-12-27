package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.CargoRequest;
import com.example.ColabManager.dto.response.CargoResponse;
import com.example.ColabManager.entity.Cargo;
import com.example.ColabManager.exception.BusinessException;
import com.example.ColabManager.exception.ResourceNotFoundException;
import com.example.ColabManager.repository.CargoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepo cargoRepository;

    @Transactional(readOnly = true)
    // GET /cargos - Lista todos os cargos
    public List<CargoResponse> findAll() {
        return cargoRepository.findAll().stream()
                .map(CargoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    // GET /cargos/{id} - Retorna detalhes de um cargo específico
    public CargoResponse findById(Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cargo não encontrado com ID: " + id));
        return CargoResponse.fromEntity(cargo);
    }

    @Transactional
    // POST /cargos - Cadastra um novo cargo
    public CargoResponse create(CargoRequest request) {

        // Verificar se o nome do cargo já existe
        if (cargoRepository.existsByNome(request.getNome())) {
            throw new BusinessException("Nome do cargo já cadastrado: " + request.getNome());
        }

        // Converter request para entity
        Cargo cargo = request.toEntity();

        // Salvar cargo
        Cargo savedCargo = cargoRepository.save(cargo);

        return CargoResponse.fromEntity(savedCargo);
    }

    @Transactional
    // PUT /cargos/{id} - Atualiza um cargo existente
    public CargoResponse update(Long id, CargoRequest request) {

        // Buscar o cargo existente
        Cargo existingCargo = cargoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cargo não encontrado com ID: " + id));

        // Verificar se está tentando alterar o nome para um já existente
        if (!existingCargo.getNome().equals(request.getNome())
                && cargoRepository.existsByNome(request.getNome())) {
            throw new BusinessException("Nome do cargo já cadastrado: " + request.getNome());
        }

        // Aplicar as atualizações
        request.applyToEntity(existingCargo);

        // Salvar as alterações
        Cargo updatedCargo = cargoRepository.save(existingCargo);

        return CargoResponse.fromEntity(updatedCargo);
    }

    // DELETE /cargos/{id} - Remove um cargo existente
    @Transactional
    public void delete(Long id) {

        // Verificar se o cargo existe
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cargo não encontrado com ID: " + id));

        // Verificar se o cargo está associado a algum funcionário
        if (cargo.getFuncionarios() != null && !cargo.getFuncionarios().isEmpty()) {
            throw new BusinessException(
                    "Não é possível deletar o cargo, pois está associado a funcionários."
            );
        }

        // Deletar o cargo
        cargoRepository.delete(cargo);
    }
}
