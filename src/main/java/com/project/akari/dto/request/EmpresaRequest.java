package com.project.akari.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmpresaRequest(
        @NotBlank(message = "O nome da empresa é obrigatório.") String nome) {

}
