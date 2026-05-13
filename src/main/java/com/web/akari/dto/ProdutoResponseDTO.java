package com.web.akari.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    BigDecimal preco,
    String medida,
    String nomeMarca,
    String nomeCategoria
) {}
