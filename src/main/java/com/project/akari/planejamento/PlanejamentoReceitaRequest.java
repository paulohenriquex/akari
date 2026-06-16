package com.project.akari.planejamento;

import jakarta.validation.constraints.NotNull;

public record PlanejamentoReceitaRequest(
        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId,
        @NotNull(message = "O ID do serviço é obrigatório") Long servicoId,
        @NotNull(message = "O ID da receita é obrigatório") Long receitaId) {
}
