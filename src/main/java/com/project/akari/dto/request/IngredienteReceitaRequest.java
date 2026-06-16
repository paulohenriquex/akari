package com.project.akari.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IngredienteReceitaRequest(
        @NotNull(message = "O ID do produto é obrigatório") Long produtoId,
        @NotNull(message = "A quantidade percapita é obrigatória") @Positive(message = "A quantidade percapita deve ser maior que zero") BigDecimal perCapita,
        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId) {

}
