package edu.unicesumar.aep;
import edu.unicesumar.aep.mapper.DoacaoMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;

public class DoacaoMapperTest {

    private DoacaoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DoacaoMapper();
    }

    @Test
    @DisplayName("Deve converter DoacaoModel para RequestDoacaoDTO corretamente")
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
                .pontoColeta("Centro")
                .build();

        RequestDoacaoDTO response = mapper.toResponse(model);

        assertNotNull(response);
        assertEquals("123", response.id());
        assertEquals("Casaco de Lã", response.item_doacao());
        assertEquals("Novo com etiqueta", response.obs());
        assertEquals(DoacaoStatus.DISPONIVEL, response.status());
        assertEquals("PR", response.estado_uf());
        assertEquals("Maringá", response.cidade());
        assertEquals(CategoriaDoacao.ROUPA, response.categoria_doacao());
        assertEquals("G", response.tamanho());
        assertEquals(EstadoConservacao.NOVO, response.estado_conservacao());
        assertEquals("Centro", response.ponto_coleta());
    }

    @Test
    @DisplayName("Deve converter RequestDoacaoDTO para DoacaoModel corretamente")
    void deveMapearDtoParaModel() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .id("123")
                .item_doacao("Tenis Esportivo")
                .obs("Usado poucas vezes")
                .status(DoacaoStatus.DISPONIVEL)
                .estado_uf("PR")
                .cidade("Maringá")
                .categoria_doacao(CategoriaDoacao.CALCADO)
                .tamanho("41")
                .estado_conservacao(EstadoConservacao.USADO)
                .ponto_coleta("Zona 7")
                .build();

        DoacaoModel model = mapper.toModel(dto);

        assertNotNull(model);
        assertEquals("Tenis Esportivo", model.getItemDoacao());
        assertEquals("Usado poucas vezes", model.getObs());
        assertEquals(DoacaoStatus.DISPONIVEL, model.getStatus());
        assertEquals("PR", model.getEstadoUf());
        assertEquals("Maringá", model.getCidade());
        assertEquals(CategoriaDoacao.CALCADO, model.getCategoriaDoacao());
        assertEquals("41", model.getTamanho());
        assertEquals(EstadoConservacao.USADO, model.getEstadoConservacao());
        assertEquals("Zona 7", model.getPontoColeta());
    }
}