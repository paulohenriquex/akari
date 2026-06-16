package com.project.akari.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaRequest(
        @NotBlank(message = "O nome da categoria é obrigatório.") String nome,

        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId) {

}
