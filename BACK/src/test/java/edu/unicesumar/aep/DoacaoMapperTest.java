package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.mapper.DoacaoMapper;
import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import edu.unicesumar.aep.model.PontoColetaModel;

public class DoacaoMapperTest {

    private DoacaoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DoacaoMapper();
    }

    @Test
    @DisplayName("Deve converter DoacaoModel para ResponseDoacaoDTO corretamente com Ponto de Coleta")
    void deveMapearModelParaResponse() {
        DoacaoModel model = DoacaoModel.builder()
                .id("123")
                .itemDoacao("Casaco de Lã")
                .obs("Novo com etiqueta")
                .status(DoacaoStatus.DISPONIVEL) 
                .estadoUf("PR")
                .cidade("Maringá")
                .categoriaDoacao(CategoriaDoacao.ROUPA) 
                .tamanho("G")
                .estadoConservacao(EstadoConservacao.NOVO) 
                .pontoColetaId("ponto-1")
                .historicoStatus(new ArrayList<>())
                .build();

        PontoColetaModel ponto = PontoColetaModel.builder()
                .id("ponto-1")
                // Assumindo que PontoColetaModel tem um campo 'nome'. Ajuste se for diferente.
                .build();

        ResponseDoacaoDTO response = mapper.toResponse(model, ponto);

        assertNotNull(response);
        assertEquals("123", response.id());
        assertEquals("Casaco de Lã", response.item_doacao());
        assertEquals(DoacaoStatus.DISPONIVEL, response.status());
    }

    @Test
    @DisplayName("Deve converter RequestDoacaoDTO para DoacaoModel corretamente")
    void deveMapearDtoParaModel() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .item_doacao("Tenis Esportivo")
                .obs("Usado poucas vezes")
                .status(DoacaoStatus.DISPONIVEL)
                .estado_uf("PR")
                .cidade("Maringá")
                .categoria_doacao(CategoriaDoacao.CALCADO)
                .tamanho("41")
                .estado_conservacao(EstadoConservacao.USADO)
                .ponto_coleta_id("ponto-1")
                .build();

        String pontoIdResolvido = "ponto-1";
        DoacaoModel model = mapper.toModel(dto, pontoIdResolvido);

        assertNotNull(model);
        assertEquals("Tenis Esportivo", model.getItemDoacao());
        assertEquals("Usado poucas vezes", model.getObs());
        assertEquals(DoacaoStatus.DISPONIVEL, model.getStatus());
        assertEquals("ponto-1", model.getPontoColetaId());
    }
}