package com.web.akari.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.akari.dto.MarcaRequestDTO;
import com.web.akari.dto.MarcaResponseDTO;
import com.web.akari.model.Marca;
import com.web.akari.repository.MarcaRepository;
import com.web.akari.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final UserRepository userRepository;

    @Transactional
    public MarcaResponseDTO criar(MarcaRequestDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setUser(userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado.")));

        Marca salva = marcaRepository.save(marca);
        return new MarcaResponseDTO(salva.getId(), salva.getNome());
    }

    @Transactional(readOnly = true)
    public List<MarcaResponseDTO> listarPorUsuario(Long userId) {
        return marcaRepository.findByUserId(userId).stream()
                .map(m -> new MarcaResponseDTO(m.getId(), m.getNome()))
                .toList();
    }

}
