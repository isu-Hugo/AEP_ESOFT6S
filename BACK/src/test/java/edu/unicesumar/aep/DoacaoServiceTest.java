package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
}