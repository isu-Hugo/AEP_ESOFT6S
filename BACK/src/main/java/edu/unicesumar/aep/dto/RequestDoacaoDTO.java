package edu.unicesumar.aep.dto;

import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record RequestDoacaoDTO(
        String id,
        String item_doacao,
        String obs,
        DoacaoStatus status,
        String estado_uf,
        String cidade,
        CategoriaDoacao categoria_doacao,
        String tamanho,
        EstadoConservacao estado_conservacao,
        String ponto_coleta
) {
}
