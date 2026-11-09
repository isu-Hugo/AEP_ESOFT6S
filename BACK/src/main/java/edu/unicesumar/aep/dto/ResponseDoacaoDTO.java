package edu.unicesumar.aep.dto;

import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import edu.unicesumar.aep.model.HistoricoStatus;
import edu.unicesumar.aep.model.PontoColetaModel;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseDoacaoDTO(
        String id,
        String item_doacao,
        String obs,
        DoacaoStatus status,
        String estado_uf,
        String cidade,
        CategoriaDoacao categoria_doacao,
        String tamanho,
        EstadoConservacao estado_conservacao,
        String ponto_coleta,
        String ponto_coleta_id,
        PontoColetaModel ponto,
        List<HistoricoStatus> historico_status
) {}