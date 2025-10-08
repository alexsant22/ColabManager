package com.example.ColabManager.service;

import com.example.ColabManager.dto.request.UsuarioCreateRequest;
import com.example.ColabManager.dto.response.UsuarioResponse;
import com.example.ColabManager.entity.Usuario;
import com.example.ColabManager.repository.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepo repository;

    @Transactional(readOnly = true)
    // GET /usuarios - Lista todos os usuários
    public List<UsuarioResponse> findAll() {
        return repository.findAll().stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    // GET /usuarios/{id} - Retorna detalhes de um usuário específico
    public UsuarioResponse findById(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return UsuarioResponse.fromEntity(usuario);
    }

    @Transactional
    // POST /usuarios - Cadastra um novo usuário
    public UsuarioResponse create(UsuarioCreateRequest request) {
        // Verificar se o username já existe
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já cadastrado: " + request.getUsername());
        }

        // Converter request para entity
        Usuario usuario = request.toEntity();

        // Salvar usuário
        Usuario savedUsuario = repository.save(usuario);

        return UsuarioResponse.fromEntity(savedUsuario);
    }

    @Transactional
    // PUT /usuarios/{id} - Atualiza um usuário existente
    public UsuarioResponse update(Long id, UsuarioCreateRequest request) {
        // Buscar o usuário existente
        Usuario existingUsuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        // Aplicar as atualizações
        request.applyToEntity(existingUsuario);

        // Salvar as mudanças
        Usuario updatedUsuario = repository.save(existingUsuario);

        return UsuarioResponse.fromEntity(updatedUsuario);
    }

    @Transactional
    // DELETE /usuarios/{id} - Deleta um usuário existente
    public void delete(Long id) {
        // Verificar se o usuário existe
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }

        // Deletar o usuário
        repository.deleteById(id);
    }
}
