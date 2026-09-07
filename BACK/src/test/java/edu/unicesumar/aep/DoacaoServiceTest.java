package edu.unicesumar.aep;

import java.util.List;
import java.util.NoSuchElementException;
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
import edu.unicesumar.aep.mapper.DoacaoMapper;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.repository.DoacaoRepository;
import edu.unicesumar.aep.service.DoacaoService;

@ExtendWith(MockitoExtension.class)
public class DoacaoServiceTest {

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private DoacaoMapper mapper;

    @InjectMocks
    private DoacaoService doacaoService;

    @Test
    @DisplayName("Deve salvar doacao com sucesso quando dados forem validados")
    public void deveSalvarDoacaoComSucesso() {

        DoacaoModel docaoModel = new DoacaoModel();
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .id("1")
                .item_doacao("Camiseta")
                .build();

        when(mapper.toModel(dto)).thenReturn(docaoModel);
        when(doacaoRepository.save(docaoModel)).thenReturn(docaoModel);
        when(mapper.toResponse(docaoModel)).thenReturn(dto);

        RequestDoacaoDTO resultado = doacaoService.save(dto);

        assertNotNull(resultado);
        assertEquals("1", resultado.id());
        verify(doacaoRepository, times(1)).save(docaoModel);
    }

    @Test
    @DisplayName("Deve retornar uma lista de doacoes no findAll")
    void deveRetornarListaDeDoacoesNoFindAll() {
        
        DoacaoModel doacaoModel = new DoacaoModel();
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .id("1")
                .item_doacao("Camiseta")
                .build();


        when(doacaoRepository.findAll()).thenReturn(List.of(doacaoModel));
        when(mapper.toResponse(doacaoModel)).thenReturn(dto);

        List<RequestDoacaoDTO> resultado = doacaoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(doacaoRepository, times(1)).findAll();
        verify(mapper, times(1)).toResponse(doacaoModel);
    }

    @Test
    @DisplayName("Deve lancar excecao ao buscar doacao por ID inexistente")
    public void deveLancarExcecaoQuandoIdNaoEncontrado() {
        
        String id = "id-inexistente";
        when(doacaoRepository.findById(id)).thenReturn(Optional.empty());

        
        assertThrows(NoSuchElementException.class, () -> {
            doacaoService.findById(id);
        });

        verify(doacaoRepository, times(1)).findById(id);
        verify(mapper, never()).toResponse(any());
    }


}