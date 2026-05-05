package com.web.akari.dto;

import java.util.List;

public record FichaTecnicaRequestDTO(
        Long id,
        String modoDePreparo,
        List<IngredienteRequestDTO> ingredientes) {
}
