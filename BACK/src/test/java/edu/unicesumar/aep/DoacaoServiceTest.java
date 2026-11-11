package edu.unicesumar.aep;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.exception.RecursoNaoEncontradoException;
import edu.unicesumar.aep.mapper.DoacaoMapper;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.PontoColetaModel;
import edu.unicesumar.aep.repository.DoacaoRepository;
import edu.unicesumar.aep.service.DoacaoService;
import edu.unicesumar.aep.service.PontoColetaService;

@ExtendWith(MockitoExtension.class)
public class DoacaoServiceTest {

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private PontoColetaService pontoColetaService;

    @Mock
    private DoacaoMapper mapper;

    @InjectMocks
    private DoacaoService doacaoService;

    @Test
    @DisplayName("Deve salvar doacao com sucesso e resolver ponto de coleta")
    public void deveSalvarDoacaoComSucesso() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .item_doacao("Camiseta")
                .ponto_coleta_id("ponto-1")
                .build();
        
        DoacaoModel model = DoacaoModel.builder().itemDoacao("Camiseta").pontoColetaId("ponto-1").build();
        PontoColetaModel ponto = PontoColetaModel.builder().id("ponto-1").build();
        ResponseDoacaoDTO responseDTO = ResponseDoacaoDTO.builder().id("1").item_doacao("Camiseta").build();

        when(pontoColetaService.findById("ponto-1")).thenReturn(Optional.of(ponto));
        when(mapper.toModel(dto, "ponto-1")).thenReturn(model);
        when(doacaoRepository.save(model)).thenReturn(model);
        when(mapper.toResponse(model, ponto)).thenReturn(responseDTO);

        ResponseDoacaoDTO resultado = doacaoService.save(dto);

        assertNotNull(resultado);
        assertEquals("1", resultado.id());
        verify(doacaoRepository, times(1)).save(model);
    }

    @Test
    @DisplayName("Deve retornar uma lista de doacoes no findAll sem filtros")
    void deveRetornarListaDeDoacoesNoFindAll() {
        DoacaoModel model = DoacaoModel.builder().id("1").pontoColetaId("ponto-1").build();
        PontoColetaModel ponto = PontoColetaModel.builder().id("ponto-1").build();
        ResponseDoacaoDTO responseDTO = ResponseDoacaoDTO.builder().id("1").build();

        when(doacaoRepository.findAll()).thenReturn(List.of(model));
        when(pontoColetaService.findById("ponto-1")).thenReturn(Optional.of(ponto));
        when(mapper.toResponse(model, ponto)).thenReturn(responseDTO);

        // Chamando o método com os 2 parâmetros exigidos agora (status e pontoColetaId)
        List<ResponseDoacaoDTO> resultado = doacaoService.findAll(null, null);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(doacaoRepository, times(1)).findAll();
        verify(mapper, times(1)).toResponse(model, ponto);
    }

    @Test
    @DisplayName("Deve lancar excecao ao buscar doacao por ID inexistente")
    public void deveLancarExcecaoQuandoIdNaoEncontrado() {
        String id = "id-inexistente";
        when(doacaoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            doacaoService.findById(id);
        });

        verify(doacaoRepository, times(1)).findById(id);
        verify(mapper, never()).toResponse(any(), any());
    }
}