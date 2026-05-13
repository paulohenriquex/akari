package com.web.akari.dto;

import java.math.BigDecimal;
import java.util.List;

public record ListaDeComprasDTO(
    List<ItensDaListaDTO> itens,
    BigDecimal custoTotalPlanejamento
) {}
