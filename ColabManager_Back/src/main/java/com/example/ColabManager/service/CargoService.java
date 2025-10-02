package com.example.ColabManager.service;

import com.example.ColabManager.dto.response.CargoResponse;
import com.example.ColabManager.repository.CargoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepo cargoRepository;

    // GET /cargos - Lista todos os cargos
    public List<CargoResponse> findAll() {
        return cargoRepository.findAll().stream()
                .map(CargoResponse::FromEntity);
    }
}
