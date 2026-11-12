package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import edu.unicesumar.aep.model.PontoColetaModel;
import edu.unicesumar.aep.repository.PontoColetaRepository;
import edu.unicesumar.aep.service.PontoColetaService;

@ExtendWith(MockitoExtension.class)
public class PontoColetaServiceTest {

    @Mock
    private PontoColetaRepository repository;

    @InjectMocks
    private PontoColetaService service;

    @Test
    @DisplayName("Deve buscar todos os pontos")
    void deveBuscarTodos() {
        PontoColetaModel ponto = PontoColetaModel.builder().id("1").build();
        when(repository.findAll()).thenReturn(List.of(ponto));

        List<PontoColetaModel> resultado = service.findAll();

        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar por id")
    void deveBuscarPorId() {
        String id = "1";
        PontoColetaModel ponto = PontoColetaModel.builder().id(id).build();
        when(repository.findById(id)).thenReturn(Optional.of(ponto));

        Optional<PontoColetaModel> resultado = service.findById(id);

        assertTrue(resultado.isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar null se nomePonto for em branco")
    void deveRetornarNullSeNomeEmBranco() {
        PontoColetaModel resultado = service.obterOuCriarPonto("PR", "Maringa", "  ");
        assertNull(resultado);
    }

    @Test
    @DisplayName("Deve criar novo ponto se nao existir")
    void deveCriarPontoSeNaoExistir() {
        String chave = service.normalizarChave("PR", "Maringa", "Centro");
        PontoColetaModel novoPonto = PontoColetaModel.builder().id("1").nome("Centro").chave(chave).build();
        
        when(repository.findByChave(chave)).thenReturn(Optional.empty());
        when(repository.save(any(PontoColetaModel.class))).thenReturn(novoPonto);

        PontoColetaModel resultado = service.obterOuCriarPonto("PR", "Maringa", "Centro");

        assertNotNull(resultado);
        assertEquals("Centro", resultado.getNome());
        verify(repository, times(1)).findByChave(chave);
        verify(repository, times(1)).save(any(PontoColetaModel.class));
    }
}