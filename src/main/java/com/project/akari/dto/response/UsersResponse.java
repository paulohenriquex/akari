package com.project.akari.dto.response;

public record UsersResponse(
        Long id,
        String email,
        String role,
        Long empresaId) {
}
