package com.web.akari.dto;

import java.math.BigDecimal;

public record IngredienteResponseDTO(
    Long produtoId,
    String nomeProduto,
    Double percapita,
    String medida,
    BigDecimal preco
) {}
