package com.project.akari.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ReceitaResponse(
        Long id,
        String nome,
        String modoDePreparo,
        Long empresaId,
        List<IngredienteReceitaResponse> ingredientes,
        BigDecimal custoTotal) {

}
