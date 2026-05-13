package com.web.akari.dto;

import java.time.LocalDate;
import java.util.List;

public record PlanejamentoRequestDTO(
    LocalDate data,
    Long servicoId,
    List<Long> fichaTecnicaIds,
    Integer quantidadePessoas,
    Long userId
) {
}
