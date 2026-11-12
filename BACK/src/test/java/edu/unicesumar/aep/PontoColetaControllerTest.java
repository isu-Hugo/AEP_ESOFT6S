package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.unicesumar.aep.controller.PontoColetaController;
import edu.unicesumar.aep.exception.RecursoNaoEncontradoException;
import edu.unicesumar.aep.model.PontoColetaModel;
import edu.unicesumar.aep.service.PontoColetaService;

@ExtendWith(MockitoExtension.class)
public class PontoColetaControllerTest {

    @Mock
    private PontoColetaService service;

    @InjectMocks
    private PontoColetaController controller;

    @Test
    @DisplayName("Deve retornar todos os pontos de coleta")
    void deveRetornarTodos() {
        PontoColetaModel ponto = PontoColetaModel.builder().id("1").nome("Centro").build();
        when(service.findAll()).thenReturn(List.of(ponto));

        ResponseEntity<List<PontoColetaModel>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar ponto de coleta por id")
    void deveRetornarPorId() {
        String id = "1";
        PontoColetaModel ponto = PontoColetaModel.builder().id(id).nome("Centro").build();
        when(service.findById(id)).thenReturn(Optional.of(ponto));

        ResponseEntity<PontoColetaModel> response = controller.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        verify(service, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lancar excecao ao nao encontrar ponto de coleta por id")
    void deveLancarExcecaoPorIdNaoEncontrado() {
        String id = "2";
        when(service.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> controller.findById(id));
        verify(service, times(1)).findById(id);
    }
}