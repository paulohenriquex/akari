package com.project.akari.dto.response;

import java.time.LocalDate;
import java.util.List;

public record PlanejamentoResponse(
        Long id,
        Long empresaId,
        Integer quantitativo,
        LocalDate data,
        List<PlanejamentoReceitaResponse> itens) {

}
