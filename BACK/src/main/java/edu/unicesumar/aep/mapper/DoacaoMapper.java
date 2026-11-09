package edu.unicesumar.aep.mapper;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
import edu.unicesumar.aep.model.PontoColetaModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoacaoMapper {

    public ResponseDoacaoDTO toResponse(DoacaoModel m) {
        return toResponse(m, null);
    }

    public ResponseDoacaoDTO toResponse(DoacaoModel m, PontoColetaModel ponto) {
        String nomePonto = ponto != null ? ponto.getNome() : null;

        return ResponseDoacaoDTO.builder()
                .id(m.getId())
                .item_doacao(m.getItemDoacao())
                .obs(m.getObs())
                .status(m.getStatus())
                .estado_uf(m.getEstadoUf())
                .cidade(m.getCidade())
                .categoria_doacao(m.getCategoriaDoacao())
                .tamanho(m.getTamanho())
                .estado_conservacao(m.getEstadoConservacao())
                .ponto_coleta_id(m.getPontoColetaId())
                .ponto_coleta(nomePonto)
                .ponto(ponto)
                .historico_status(m.getHistoricoStatus())
                .build();
    }

    public DoacaoModel toModel(RequestDoacaoDTO dto, String pontoColetaId) {
        List<HistoricoStatus> historico = new ArrayList<>();
        historico.add(HistoricoStatus.builder()
                .status(DoacaoStatus.DISPONIVEL)
                .dataHora(Instant.now())
                .observacao("Doação cadastrada")
                .build());

        return DoacaoModel.builder()
                .itemDoacao(dto.item_doacao())
                .obs(dto.obs())
                .status(DoacaoStatus.DISPONIVEL)
                .estadoUf(dto.estado_uf())
                .cidade(dto.cidade())
                .categoriaDoacao(dto.categoria_doacao())
                .tamanho(dto.tamanho())
                .estadoConservacao(dto.estado_conservacao())
                .pontoColetaId(pontoColetaId)
                .historicoStatus(historico)
                .build();
    }

    public DoacaoModel toModel(RequestDoacaoDTO dto) {
        return toModel(dto, dto.ponto_coleta_id());
    }
}