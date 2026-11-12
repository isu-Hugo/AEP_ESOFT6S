package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import edu.unicesumar.aep.exception.GlobalExceptionHandler;
import edu.unicesumar.aep.exception.RecursoNaoEncontradoException;
import edu.unicesumar.aep.exception.RegraDeNegocioException;
import edu.unicesumar.aep.exception.StatusConflictException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve tratar RecursoNaoEncontradoException")
    void deveTratarRecursoNaoEncontrado() {
        RecursoNaoEncontradoException ex = new RecursoNaoEncontradoException("Nao encontrado");
        ResponseEntity<ProblemDetail> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nao encontrado", response.getBody().getDetail());
    }

    @Test
    @DisplayName("Deve tratar RegraDeNegocioException")
    void deveTratarRegraDeNegocio() {
        RegraDeNegocioException ex = new RegraDeNegocioException("Regra violada");
        ResponseEntity<ProblemDetail> response = handler.handleRegraDeNegocio(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Regra violada", response.getBody().getDetail());
    }

    @Test
    @DisplayName("Deve tratar Exception generica")
    void deveTratarExceptionGenerica() {
        Exception ex = new Exception("Erro interno");
        ResponseEntity<ProblemDetail> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}