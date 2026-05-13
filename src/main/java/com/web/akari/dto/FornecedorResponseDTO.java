package com.web.akari.dto;

public record FornecedorResponseDTO(
    Long id,
    String nome,
    String cnpj,
    String telefone,
    String email,
    EnderecoDTO endereco
) {}
