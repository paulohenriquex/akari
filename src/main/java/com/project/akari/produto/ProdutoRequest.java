package com.project.akari.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório") String nome,
        @NotNull(message = "O preço é obrigatório") @Positive(message = "O preço deve ser maior que zero") BigDecimal preco,
        @NotBlank(message = "A medida é obrigatória") String medida,
        Long marcaId,
        Long categoriaId,
        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId) {
}
