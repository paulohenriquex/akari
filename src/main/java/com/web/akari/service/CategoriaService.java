package com.web.akari.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.akari.dto.CategoriaRequestDTO;
import com.web.akari.dto.CategoriaResponseDTO;
import com.web.akari.model.Categoria;
import com.web.akari.model.User;
import com.web.akari.repository.CategoriaRepository;
import com.web.akari.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        categoria.setUser(user);

        Categoria salva = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(salva.getId(), salva.getNome());
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarPorUsuario(Long userId) {
        return categoriaRepository.findByUserId(userId).stream()
                .map(c -> new CategoriaResponseDTO(c.getId(), c.getNome()))
                .toList();
    }
}
