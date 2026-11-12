package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.unicesumar.aep.controller.DoacaoController;
import edu.unicesumar.aep.dto.AtualizarStatusDTO;
import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
import edu.unicesumar.aep.service.DoacaoService;

@ExtendWith(MockitoExtension.class)
public class DoacaoControllerTest {

    @Mock
    private DoacaoService doacaoService;

    @InjectMocks
    private DoacaoController doacaoController;

    @Test
    @DisplayName("Deve retornar lista de doacoes")
    void deveRetornarListaDoacoes() {
        ResponseDoacaoDTO dto = ResponseDoacaoDTO.builder().id("1").build();
        when(doacaoService.findAll(null, null)).thenReturn(List.of(dto));

        ResponseEntity<List<ResponseDoacaoDTO>> response = doacaoController.findAll(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Deve salvar doacao e retornar CREATED")
    void deveSalvarDoacao() {
        // Necessário para o ServletUriComponentsBuilder não dar erro
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        RequestDoacaoDTO requestDto = RequestDoacaoDTO.builder().item_doacao("Teste").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").item_doacao("Teste").build();
        
        when(doacaoService.save(any(RequestDoacaoDTO.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDoacaoDTO> response = doacaoController.save(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("1", response.getBody().id());
    }

    @Test
    @DisplayName("Deve buscar doacao por ID")
    void deveBuscarPorId() {
        ResponseDoacaoDTO dto = ResponseDoacaoDTO.builder().id("1").build();
        when(doacaoService.findById("1")).thenReturn(dto);

        ResponseEntity<ResponseDoacaoDTO> response = doacaoController.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().id());
    }

    @Test
    @DisplayName("Deve atualizar status da doacao")
    void deveAtualizarStatus() {
        AtualizarStatusDTO atualizarDto = new AtualizarStatusDTO(DoacaoStatus.RESERVADO, "Motivo");
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").status(DoacaoStatus.RESERVADO).build();
        
        when(doacaoService.atualizarStatus(eq("1"), any(AtualizarStatusDTO.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDoacaoDTO> response = doacaoController.atualizarStatus("1", atualizarDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DoacaoStatus.RESERVADO, response.getBody().status());
    }

    @Test
    @DisplayName("Deve buscar historico de status")
    void deveBuscarHistorico() {
        HistoricoStatus historico = HistoricoStatus.builder().observacao("Teste").build();
        when(doacaoService.findHistorico("1")).thenReturn(List.of(historico));

        ResponseEntity<List<HistoricoStatus>> response = doacaoController.findHistorico("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Deve buscar doacoes por ponto de coleta")
    void deveBuscarPorPontoColeta() {
        ResponseDoacaoDTO dto = ResponseDoacaoDTO.builder().id("1").build();
        when(doacaoService.findByPontoColeta("ponto-1")).thenReturn(List.of(dto));

        ResponseEntity<List<ResponseDoacaoDTO>> response = doacaoController.findDoacoesPorPonto("ponto-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}