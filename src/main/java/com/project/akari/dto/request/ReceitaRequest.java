package com.project.akari.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReceitaRequest(
        @NotBlank(message = "O nome da receita é obrigatório") String nome,
        @NotBlank(message = "O modo de preparo é obrigatório") String modoDePreparo,
        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId,
        @NotEmpty(message = "A receita precisa de pelo menos 1 ingrediente") @Valid List<IngredienteReceitaRequest> ingredientes) {

}
