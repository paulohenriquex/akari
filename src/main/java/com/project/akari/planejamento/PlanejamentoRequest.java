package com.project.akari.planejamento;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanejamentoRequest(
        @NotNull(message = "O ID da empresa é obrigatório") Long empresaId,
        @NotNull(message = "O quantitativo é obrigatório") @Positive(message = "O quantitativo deve ser maior que zero") Integer quantitativo,
        @NotNull(message = "A data é obrigatória") LocalDate data,
        @NotEmpty(message = "O planejamento precisa de pelo menos 1 item") @Valid List<PlanejamentoReceitaRequest> itens) {

}
