package edu.unicesumar.aep.mapper;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import org.springframework.stereotype.Component;

@Component
public class DoacaoMapper {

    public RequestDoacaoDTO toResponse(DoacaoModel m){
        return RequestDoacaoDTO.builder()
                .id(m.getId())
                .nome_doador(m.getNomeDoador())
                .item_doacao(m.getItemDoacao())
                .obs(m.getObs())
                .status(m.getStatus())
                .estado_uf(m.getEstadoUf())
                .cidade(m.getCidade())
                .build();
    }

}