package com.web.akari.dto;

import java.util.List;

public record FichaTecnicaRequestDTO(
    String nome,
    String modoDePreparo,
    List<IngredienteRequestDTO> ingredientes,
    Long userId
) {}
