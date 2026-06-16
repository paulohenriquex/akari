package com.project.akari.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsersRequest(
                @NotBlank(message = "O email é obrigatório") @Email(message = "Formato do email inválido.") String email,
                @NotBlank(message = "A senha é obrigatória.") @Size(message = "A senha deve ter no mínimo 6 caracteres.") String password,
                @NotBlank(message = "A role é obrigatória.") String role,
                @NotBlank(message = "O ID da empresa é obrigatório.") Long empresaId) {
}
