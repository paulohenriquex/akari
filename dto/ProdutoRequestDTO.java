package com.web.akari.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal preco,
        String medida,
        Long marcaId,
        Long categoriaId,
        Long userId) {
}