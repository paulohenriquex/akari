package com.web.akari.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.web.akari.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;
import com.web.akari.dto.ProdutoRequestDTO;
import com.web.akari.exception.ResourceNotFoundException;
import com.web.akari.dto.ProdutoResponseDTO;
import com.web.akari.model.Produto;
import com.web.akari.repository.CategoriaRepository;
import com.web.akari.repository.MarcaRepository;
import com.web.akari.repository.ProdutoRepository;
import com.web.akari.model.User;
import com.web.akari.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

        private final ProdutoRepository produtoRepository;
        private final MarcaRepository marcaRepository;
        private final CategoriaRepository categoriaRepository;
        private final UserRepository userRepository;

        @Transactional
        public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
                User user = userRepository.findById(dto.userId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Usuário com ID " + dto.userId() + " não encontrado."));

                // Verifica se já existe um produto com o mesmo nome (ignorando
                // maiúsculas/minúsculas) para o usuário
                produtoRepository.findByNomeIgnoreCaseAndUser(dto.nome(), user).ifPresent(p -> {
                        throw new DuplicateResourceException("Já existe um produto com o nome '" + dto.nome() + "'.");
                });

                Produto produto = new Produto();
                produto.setNome(dto.nome());
                produto.setPreco(dto.preco());
                produto.setMedida(dto.medida());

                produto.setUser(user);
                produto.setMarca(marcaRepository.findById(dto.marcaId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Marca com ID " + dto.marcaId() + " não encontrada.")));
                produto.setCategoria(categoriaRepository.findById(dto.categoriaId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Categoria com ID " + dto.categoriaId() + " não encontrada.")));

                Produto salvo = produtoRepository.save(produto);
                return converterParaDTO(salvo);
        }

        @Transactional(readOnly = true)
        public List<ProdutoResponseDTO> listarPorUsuario(Long userId) {
                return produtoRepository.findByUserId(userId).stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        private ProdutoResponseDTO converterParaDTO(Produto p) {
                return new ProdutoResponseDTO(
                                p.getId(),
                                p.getNome(),
                                p.getPreco(),
                                p.getMedida(),
                                p.getMarca() != null ? p.getMarca().getNome() : "Sem marca.",
                                p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria");
        }
}
