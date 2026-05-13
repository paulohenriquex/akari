package com.web.akari.dto;

import java.math.BigDecimal;

public record ItensDaListaDTO(
    Long produtoId,
    String nomeProduto,
    Double quantidade,
    String medida,
    BigDecimal custoItem
) {}