package com.web.akari.dto;

import java.math.BigDecimal;

public record FichaTecnicaCustoDTO(
        Long id,
        String nomeReceita,
        BigDecimal custoTotal) {
}
