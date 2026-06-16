package com.project.akari.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicoRequest(
        @NotBlank(message = "O nome do serviço é obrigatório.") String nome,

        @NotNull(message = "O ID da empresa é obrigatório.") Long empresaId

) {

}
