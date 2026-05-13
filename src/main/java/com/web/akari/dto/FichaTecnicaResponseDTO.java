package com.web.akari.dto;

import java.util.List;

public record FichaTecnicaResponseDTO(
    Long id,
    String nome,
    String modoDePreparo,
    List<IngredienteResponseDTO> ingredientes
) {}
