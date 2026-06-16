package com.project.akari.marca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MarcaRequest(
        @NotBlank(message = "O nome da marca é obrigatório.") String nome,

        @NotNull(message = "O ID da empresa é obrigatório.") Long empresaId) {

}
