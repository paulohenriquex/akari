package com.web.akari.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.akari.dto.FichaTecnicaRequestDTO;
import com.web.akari.dto.FichaTecnicaResponseDTO;
import com.web.akari.exception.ResourceNotFoundException;
import com.web.akari.dto.IngredienteResponseDTO;
import com.web.akari.model.FichaTecnica;
import com.web.akari.model.Ingrediente;
import com.web.akari.model.Produto;
import com.web.akari.model.User;
import com.web.akari.repository.FichaTecnicaRepository;
import com.web.akari.repository.ProdutoRepository;
import com.web.akari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FichaTecnicaService {

        private final FichaTecnicaRepository fichaTecnicaRepository;
        private final ProdutoRepository produtoRepository;
        private final UserRepository userRepository;

        @Transactional // Usando a anotação do Spring
        public FichaTecnicaResponseDTO criar(FichaTecnicaRequestDTO dto) {
                User user = userRepository.findById(dto.userId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Usuário com ID " + dto.userId() + " não encontrado."));

                FichaTecnica fichaTecnica = new FichaTecnica();
                fichaTecnica.setNome(dto.nome());
                fichaTecnica.setModoDePreparo(dto.modoDePreparo());
                fichaTecnica.setUser(user);

                // Refatorando o loop para uma abordagem mais funcional com Streams
                List<Ingrediente> ingredientes = dto.ingredientes().stream()
                                .map(ingredienteDTO -> {
                                        Produto produto = produtoRepository.findById(ingredienteDTO.produtoId())
                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                        "Produto com ID " + ingredienteDTO.produtoId()
                                                                                        + " não encontrado."));
                                        Ingrediente ingrediente = new Ingrediente();
                                        ingrediente.setProduto(produto);
                                        ingrediente.setPercapita(ingredienteDTO.percapita());
                                        ingrediente.setFichaTecnica(fichaTecnica); // Essencial para o relacionamento
                                                                                   // bidirecional
                                        return ingrediente;
                                }).toList();

                fichaTecnica.setIngredientes(ingredientes);
                FichaTecnica salva = fichaTecnicaRepository.save(fichaTecnica);
                return converterParaResponseDTO(salva);
        }

        @Transactional(readOnly = true) // Boa prática para operações de leitura
        public List<FichaTecnicaResponseDTO> listarPorUsuario(Long userId) {
                return fichaTecnicaRepository.findByUserId(userId).stream()
                                .map(this::converterParaResponseDTO).toList();
        }

        private FichaTecnicaResponseDTO converterParaResponseDTO(FichaTecnica fichaTecnica) {
                List<IngredienteResponseDTO> ingredientesDTO = fichaTecnica.getIngredientes().stream()
                                .map(ing -> new IngredienteResponseDTO(ing.getProduto().getId(),
                                                ing.getProduto().getNome(),
                                                ing.getPercapita(), ing.getProduto().getMedida(),
                                                ing.getProduto().getPreco()))
                                .toList();

                return new FichaTecnicaResponseDTO(fichaTecnica.getId(), fichaTecnica.getNome(),
                                fichaTecnica.getModoDePreparo(), ingredientesDTO);
        }

}