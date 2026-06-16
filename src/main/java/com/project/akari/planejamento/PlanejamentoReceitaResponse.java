package com.project.akari.planejamento;

public record PlanejamentoReceitaResponse(
                Long id,
                Long empresaId,
                Long planejamentoId,
                Long servicoId,
                Long receitaId) {
}
