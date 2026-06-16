package com.project.akari.receita;

import java.math.BigDecimal;
import java.util.List;

import com.project.akari.ingrediente.IngredienteReceitaResponse;

public record ReceitaResponse(
        Long id,
        String nome,
        String modoDePreparo,
        Long empresaId,
        List<IngredienteReceitaResponse> ingredientes,
        BigDecimal custoTotal) {

}
