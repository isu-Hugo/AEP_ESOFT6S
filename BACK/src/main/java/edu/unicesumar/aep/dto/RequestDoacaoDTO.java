package edu.unicesumar.aep.dto;

import edu.unicesumar.aep.model.DoacaoStatus;
import lombok.Builder;

@Builder
public record RequestDoacaoDTO(
        String id,
        String nome_doador,
        String item_doacao,
        String obs,
        DoacaoStatus status,
        String estado_uf,
        String cidade
) {
}
