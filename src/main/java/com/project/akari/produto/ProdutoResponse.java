package com.project.akari.produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        String medida,
        Long marcaId,
        Long categoriaId,
        Long empresaId) {

}
