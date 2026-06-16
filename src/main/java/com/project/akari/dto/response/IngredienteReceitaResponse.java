package com.project.akari.dto.response;

import java.math.BigDecimal;

public record IngredienteReceitaResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        BigDecimal perCapita,
        BigDecimal custoItem) {

}
