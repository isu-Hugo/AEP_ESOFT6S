package edu.unicesumar.aep.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDoacaoDTO(
        String id,
        String item_doacao,
        String obs,
        DoacaoStatus status,
        String estado_uf,
        String cidade,
        @JsonAlias("categoria")
        CategoriaDoacao categoria_doacao,
        String tamanho,
        EstadoConservacao estado_conservacao,
        String ponto_coleta,
        String ponto_coleta_id

) {
}
