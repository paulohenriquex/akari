package com.web.akari.service;

import com.web.akari.dto.MarcaRequestDTO;
import com.web.akari.dto.MarcaResponseDTO;
import com.web.akari.exception.DuplicateResourceException;
import com.web.akari.exception.ResourceNotFoundException;
import com.web.akari.model.Marca;
import com.web.akari.model.User;
import com.web.akari.repository.MarcaRepository;
import com.web.akari.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final UserRepository userRepository;

    @Transactional
    public MarcaResponseDTO criar(MarcaRequestDTO dto) {
        User user = userRepository
            .findById(dto.userId())
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Usuário com ID " + dto.userId() + " não encontrado."
                )
            );

        // Verifica se já existe uma marca com o mesmo nome (ignorando
        // maiúsculas/minúsculas) para o usuário
        marcaRepository
            .findByNomeIgnoreCaseAndUser(dto.nome(), user)
            .ifPresent(m -> {
                throw new DuplicateResourceException(
                    "Já existe uma marca com o nome '" + dto.nome() + "'."
                );
            });

        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setUser(user);
        Marca salva = marcaRepository.save(marca);
        return new MarcaResponseDTO(salva.getId(), salva.getNome());
    }

    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> listarPorUsuario(Long userId) {
        return marcaRepository
            .findByUserId(userId)
            .stream()
            .map(m -> new MarcaResponseDTO(m.getId(), m.getNome()))
            .toList();
    }
}
