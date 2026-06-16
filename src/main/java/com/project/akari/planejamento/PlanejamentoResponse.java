package com.project.akari.planejamento;

import java.time.LocalDate;
import java.util.List;

public record PlanejamentoResponse(
        Long id,
        Long empresaId,
        Integer quantitativo,
        LocalDate data,
        List<PlanejamentoReceitaResponse> itens) {

}
