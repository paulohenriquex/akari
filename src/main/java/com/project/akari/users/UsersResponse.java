package com.project.akari.users;

public record UsersResponse(
        Long id,
        String email,
        String role,
        Long empresaId) {
}
