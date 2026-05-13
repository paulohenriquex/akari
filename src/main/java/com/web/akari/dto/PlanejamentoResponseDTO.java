package com.web.akari.dto;

import java.time.LocalDate;
import java.util.List;

public record PlanejamentoResponseDTO(
    Long id,
    LocalDate data,
    ServicoDTO servico,
    Integer quantidadePessoas,
    List<FichaTecnicaResponseDTO> fichasTecnicas,
    ListaDeComprasDTO listaDeCompras
) {
}
