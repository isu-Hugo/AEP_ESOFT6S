package edu.unicesumar.aep.dto;

import edu.unicesumar.aep.model.DoacaoStatus;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
        @NotNull(message = "O novo status é obrigatório.")
        DoacaoStatus status,
        String observacao
) {}