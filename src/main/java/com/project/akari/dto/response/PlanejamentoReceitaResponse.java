package com.project.akari.dto.response;

public record PlanejamentoReceitaResponse(
                Long id,
                Long empresaId,
                Long planejamentoId,
                Long servicoId,
                Long receitaId) {
}
