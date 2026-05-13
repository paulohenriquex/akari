package com.web.akari.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.akari.dto.FichaTecnicaResponseDTO;
import com.web.akari.dto.IngredienteResponseDTO;
import com.web.akari.dto.ItensDaListaDTO;
import com.web.akari.dto.ListaDeComprasDTO;
import com.web.akari.dto.PlanejamentoRequestDTO;
import com.web.akari.dto.PlanejamentoResponseDTO;
import com.web.akari.dto.ServicoDTO;
import com.web.akari.model.FichaTecnica;
import com.web.akari.model.Ingrediente;
import com.web.akari.model.Planejamento;
import com.web.akari.model.Produto;
import com.web.akari.model.Servico;
import com.web.akari.model.User;
import com.web.akari.repository.FichaTecnicaRepository;
import com.web.akari.repository.PlanejamentoRepository;
import com.web.akari.repository.ServicoRepository;
import com.web.akari.repository.UserRepository;

@Service
public class PlanejamentoService {

        private final PlanejamentoRepository planejamentoRepository;
        private final UserRepository userRepository;
        private final ServicoRepository servicoRepository;
        private final FichaTecnicaRepository fichaTecnicaRepository;

        public PlanejamentoService(PlanejamentoRepository planejamentoRepository, UserRepository userRepository,
                        ServicoRepository servicoRepository, FichaTecnicaRepository fichaTecnicaRepository) {
                this.planejamentoRepository = planejamentoRepository;
                this.userRepository = userRepository;
                this.servicoRepository = servicoRepository;
                this.fichaTecnicaRepository = fichaTecnicaRepository;
        }

        @Transactional
        public PlanejamentoResponseDTO criar(PlanejamentoRequestDTO dto) {
                User usuario = userRepository.findById(dto.userId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                Servico servico = servicoRepository.findById(dto.servicoId())
                                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

                List<FichaTecnica> fichasTecnicas = fichaTecnicaRepository.findAllById(
                                dto.fichaTecnicaIds());

                Planejamento novoPlanejamento = new Planejamento();
                novoPlanejamento.setData(dto.data());
                novoPlanejamento.setServico(servico);
                novoPlanejamento.setQuantidadePessoas(dto.quantidadePessoas());
                novoPlanejamento.setFichasTecnicas(new HashSet<>(fichasTecnicas));
                novoPlanejamento.setUsuario(usuario);

                Planejamento planejamentoSalvo = planejamentoRepository.save(novoPlanejamento);
                // Calcular lista de compras
                ListaDeComprasDTO listaDeCompras = calcularListaDeCompras(planejamentoSalvo);

                // Retornar resposta
                return toResponseDTO(planejamentoSalvo,listaDeCompras);
        }

        @Transactional(readOnly = true)
        public List<PlanejamentoResponseDTO> listarPorUsuario(Long userId) {
                return planejamentoRepository.findByUsuarioId(userId)
                .stream()
                .map(planejamento -> toResponseDTO(planejamento,calcularListaDeCompras(planejamento)))
                .toList();
        }

        private ListaDeComprasDTO calcularListaDeCompras(Planejamento planejamento) {

                List<ItensDaListaDTO> itens = new ArrayList<>();

                Integer quantidadePessoas = planejamento.getQuantidadePessoas();

                // Percorre as fichas
                for (FichaTecnica ficha : planejamento.getFichasTecnicas()) {
                        // Percorre ingredientes
                        for (Ingrediente ingrediente : ficha.getIngredientes()) {
                                Produto produto = ingrediente.getProduto();
                                double quantidadeNecessaria = (ingrediente.getPercapita()/1000)* quantidadePessoas;
                                boolean produtoJaExiste = false;

                                // Procura produto na lista
                                for (int i = 0; i < itens.size(); i++) {

                                        ItensDaListaDTO item = itens.get(i);

                                        // Se já existe
                                        if (item.produtoId().equals(produto.getId())) {

                                                double novaQuantidade = item.quantidade() + quantidadeNecessaria;

                                                BigDecimal novoCusto = produto.getPreco().multiply(
                                                                BigDecimal.valueOf(
                                                                                novaQuantidade));

                                                ItensDaListaDTO itemAtualizado = new ItensDaListaDTO(
                                                                produto.getId(),
                                                                produto.getNome(),
                                                                novaQuantidade,
                                                                produto.getMedida(),
                                                                novoCusto);

                                                itens.set(i, itemAtualizado);

                                                produtoJaExiste = true;

                                                break;
                                        }
                                }

                                // Se não existe adiciona
                                if (!produtoJaExiste) {

                                        BigDecimal custoItem = produto.getPreco().multiply(
                                                        BigDecimal.valueOf(
                                                                        quantidadeNecessaria));

                                        ItensDaListaDTO novoItem = new ItensDaListaDTO(
                                                        produto.getId(),
                                                        produto.getNome(),
                                                        quantidadeNecessaria,
                                                        produto.getMedida(),
                                                        custoItem);

                                        itens.add(novoItem);
                                }
                        }
                }

                // Soma custo total
                BigDecimal custoTotal = BigDecimal.ZERO;

                for (ItensDaListaDTO item : itens) {
                        custoTotal = custoTotal.add(item.custoItem());
                }

                return new ListaDeComprasDTO(
                                itens,
                                custoTotal);
        }

        private PlanejamentoResponseDTO toResponseDTO(
                        Planejamento planejamento,
                        ListaDeComprasDTO listaDeCompras) {

                ServicoDTO servicoDTO = new ServicoDTO(
                                planejamento.getServico().getId(),
                                planejamento.getServico().getNome());

                List<FichaTecnicaResponseDTO> fichasTecnicasDTO = new ArrayList<>();

                for (FichaTecnica ficha : planejamento.getFichasTecnicas()) {
                        List<IngredienteResponseDTO> ingredientesDTO = new ArrayList<>();

                        for (Ingrediente ingrediente : ficha.getIngredientes()) {
                                Produto produto = ingrediente.getProduto();

                                IngredienteResponseDTO ingredienteDTO = new IngredienteResponseDTO(
                                                produto.getId(),
                                                produto.getNome(),
                                                ingrediente.getPercapita(),
                                                produto.getMedida(),
                                                produto.getPreco());

                                ingredientesDTO.add(ingredienteDTO);
                        }

                        FichaTecnicaResponseDTO fichaDTO = new FichaTecnicaResponseDTO(
                                        ficha.getId(),
                                        ficha.getNome(),
                                        ficha.getModoDePreparo(),
                                        ingredientesDTO);

                        fichasTecnicasDTO.add(fichaDTO);
                }

                return new PlanejamentoResponseDTO(
                                planejamento.getId(),
                                planejamento.getData(),
                                servicoDTO,
                                planejamento.getQuantidadePessoas(),
                                fichasTecnicasDTO,
                                listaDeCompras);
        }
}
