package edu.unicesumar.aep.mapper;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
public class DoacaoMapper {

    public RequestDoacaoDTO toResponse(DoacaoModel m){
        return RequestDoacaoDTO.builder()
                .id(m.getId())
                .item_doacao(m.getItemDoacao())
                .obs(m.getObs())
                .status(m.getStatus())
                .estado_uf(m.getEstadoUf())
                .cidade(m.getCidade())
                .categoria_doacao(m.getCategoriaDoacao())
                .tamanho(m.getTamanho())
                .estado_conservacao(m.getEstadoConservacao())
                .ponto_coleta(m.getPontoColeta())
                .build();
    }
}