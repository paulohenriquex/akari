package com.web.akari.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.web.akari.dto.ProdutoRequestDTO;
import com.web.akari.dto.ProdutoResponseDTO;
import com.web.akari.model.Produto;
import com.web.akari.repository.CategoriaRepository;
import com.web.akari.repository.MarcaRepository;
import com.web.akari.repository.ProdutoRepository;
import com.web.akari.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class ProdutoService {

        private final ProdutoRepository produtoRepository;
        private final MarcaRepository marcaRepository;
        private final CategoriaRepository categoriaRepository;
        private final UserRepository userRepository;

        @Transactional
        public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
                Produto produto = new Produto();
                produto.setNome(dto.nome());
                produto.setPreco(dto.preco());
                produto.setMedida(dto.medida());

                produto.setMarca(marcaRepository.findById(dto.marcaId())
                                .orElseThrow(() -> new RuntimeException("Marca não encontrada.")));
                produto.setCategoria(categoriaRepository.findById(dto.categoriaId())
                                .orElseThrow(() -> new RuntimeException("Categoria não encontrada.")));
                produto.setUser(userRepository.findById(dto.userId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado.")));

                Produto salvo = produtoRepository.save(produto);
                return converterParaDTO(salvo);
        }

        @Transactional(readOnly = true)
        public List<ProdutoResponseDTO> listarTodos() {
                return produtoRepository.findAll().stream()
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
                                p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria"
                                );
        }
}
