package com.web.akari.dto;

public record FornecedorRequestDTO(
        String nome,
        String cnpj,
        String telefone,
        String email,
        EnderecoDTO endereco,
        Long userId
) {
}