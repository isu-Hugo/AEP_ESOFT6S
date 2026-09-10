package edu.unicesumar.aep;

import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.unicesumar.aep.controller.DoacaoController;
import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.service.DoacaoService;

@ExtendWith(MockitoExtension.class)
public class DoacaoControllerTest {

    @Mock
    private DoacaoService doacaoService;

    @InjectMocks
    private DoacaoController doacaoController;

    @Test
    @DisplayName("Deve retornar status 200 OK no findAll")
    void deveBuscarTodas() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder().id("1").item_doacao("Camiseta").build();
        when(doacaoService.findAll()).thenReturn(List.of(dto));

        ResponseEntity<List<RequestDoacaoDTO>> response = doacaoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(doacaoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar status 201 Created no save")
    void deveSalvarDoacao() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        RequestDoacaoDTO dtoInput = RequestDoacaoDTO.builder().item_doacao("Camiseta").build();
        RequestDoacaoDTO dtoOutput = RequestDoacaoDTO.builder().id("123").item_doacao("Camiseta").build();

        when(doacaoService.save(dtoInput)).thenReturn(dtoOutput);

        ResponseEntity<RequestDoacaoDTO> response = doacaoController.save(dtoInput);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("123", response.getBody().id());
        verify(doacaoService, times(1)).save(dtoInput);
    }

    @Test
    @DisplayName("Deve retornar status 200 OK no findById")
    void deveBuscarPorId() {
        String id = "123";
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder().id(id).item_doacao("Camiseta").build();
        when(doacaoService.findById(id)).thenReturn(dto);

        ResponseEntity<RequestDoacaoDTO> response = doacaoController.findById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().id());
        verify(doacaoService, times(1)).findById(id);
    }
}