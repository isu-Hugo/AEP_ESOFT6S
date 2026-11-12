package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.unicesumar.aep.dto.AtualizarStatusDTO;
import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.exception.RecursoNaoEncontradoException;
import edu.unicesumar.aep.exception.RegraDeNegocioException;
import edu.unicesumar.aep.exception.StatusConflictException;
import edu.unicesumar.aep.mapper.DoacaoMapper;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
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
    @DisplayName("findAll - com filtros retorna lista filtrada")
    void findAll_comFiltros() {
        DoacaoModel model = DoacaoModel.builder().id("1").pontoColetaId("p1").build();
        PontoColetaModel ponto = PontoColetaModel.builder().id("p1").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(doacaoRepository.findByFiltros(DoacaoStatus.DISPONIVEL, "p1")).thenReturn(List.of(model));
        when(pontoColetaService.findById("p1")).thenReturn(Optional.of(ponto));
        when(mapper.toResponse(model, ponto)).thenReturn(responseDto);

        List<ResponseDoacaoDTO> resultado = doacaoService.findAll(DoacaoStatus.DISPONIVEL, "p1");

        assertEquals(1, resultado.size());
        verify(doacaoRepository).findByFiltros(DoacaoStatus.DISPONIVEL, "p1");
    }

    @Test
    @DisplayName("findAll - sem filtros retorna tudo")
    void findAll_semFiltros() {
        DoacaoModel model = DoacaoModel.builder().id("1").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(doacaoRepository.findAll()).thenReturn(List.of(model));
        when(mapper.toResponse(model, null)).thenReturn(responseDto);

        List<ResponseDoacaoDTO> resultado = doacaoService.findAll(null, null);

        assertEquals(1, resultado.size());
        verify(doacaoRepository).findAll();
    }

    @Test
    @DisplayName("save - com nome do ponto cria ou obtem ponto")
    void save_comNomePonto() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .estado_uf("PR").cidade("Maringa").ponto_coleta("Centro")
                .build();
        PontoColetaModel ponto = PontoColetaModel.builder().id("p1").build();
        DoacaoModel model = DoacaoModel.builder().id("1").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(pontoColetaService.obterOuCriarPonto("PR", "Maringa", "Centro")).thenReturn(ponto);
        when(mapper.toModel(dto, "p1")).thenReturn(model);
        when(doacaoRepository.save(model)).thenReturn(model);
        when(mapper.toResponse(model, ponto)).thenReturn(responseDto);

        ResponseDoacaoDTO resultado = doacaoService.save(dto);

        assertNotNull(resultado);
        verify(pontoColetaService).obterOuCriarPonto("PR", "Maringa", "Centro");
    }

    @Test
    @DisplayName("save - com id do ponto busca ponto existente")
    void save_comIdPonto() {
        RequestDoacaoDTO dto = RequestDoacaoDTO.builder()
                .ponto_coleta_id("p1")
                .build();
        PontoColetaModel ponto = PontoColetaModel.builder().id("p1").build();
        DoacaoModel model = DoacaoModel.builder().id("1").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(pontoColetaService.findById("p1")).thenReturn(Optional.of(ponto));
        when(mapper.toModel(dto, "p1")).thenReturn(model);
        when(doacaoRepository.save(model)).thenReturn(model);
        when(mapper.toResponse(model, ponto)).thenReturn(responseDto);

        ResponseDoacaoDTO resultado = doacaoService.save(dto);

        assertNotNull(resultado);
        verify(pontoColetaService).findById("p1");
    }

    @Test
    @DisplayName("findById - lanca excecao quando nao existe")
    void findById_naoExiste() {
        when(doacaoRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> doacaoService.findById("1"));
    }

    @Test
    @DisplayName("findHistorico - retorna lista de historico")
    void findHistorico() {
        List<HistoricoStatus> historico = new ArrayList<>();
        historico.add(HistoricoStatus.builder().observacao("obs").build());
        DoacaoModel model = new DoacaoModel();
        model.setHistoricoStatus(historico);

        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model));
        List<HistoricoStatus> resultado = doacaoService.findHistorico("1");
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("findByPontoColeta - retorna lista de doacoes")
    void findByPontoColeta() {
        PontoColetaModel ponto = PontoColetaModel.builder().id("p1").build();
        DoacaoModel model = DoacaoModel.builder().id("1").pontoColetaId("p1").build();
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(pontoColetaService.findById("p1")).thenReturn(Optional.of(ponto));
        when(doacaoRepository.findByPontoColetaId("p1")).thenReturn(List.of(model));
        when(pontoColetaService.findById("p1")).thenReturn(Optional.of(ponto)); 
        when(mapper.toResponse(model, ponto)).thenReturn(responseDto);

        List<ResponseDoacaoDTO> resultado = doacaoService.findByPontoColeta("p1");
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("findByPontoColeta - lanca excecao se ponto nao existir")
    void findByPontoColeta_pontoNaoExiste() {
        when(pontoColetaService.findById("p1")).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> doacaoService.findByPontoColeta("p1"));
    }

    @Test
    @DisplayName("atualizarStatus - sucesso de DISPONIVEL para RESERVADO")
    void atualizarStatus_sucesso() {
        DoacaoModel model = DoacaoModel.builder().id("1").status(DoacaoStatus.DISPONIVEL).build();
        AtualizarStatusDTO dto = new AtualizarStatusDTO(DoacaoStatus.RESERVADO, "Reserva");
        ResponseDoacaoDTO responseDto = ResponseDoacaoDTO.builder().id("1").build();

        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model));
        when(doacaoRepository.updateStatusAtomico(eq("1"), eq(DoacaoStatus.DISPONIVEL), eq(DoacaoStatus.RESERVADO), any())).thenReturn(true);
        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model)); 
        when(mapper.toResponse(model, null)).thenReturn(responseDto);

        ResponseDoacaoDTO resultado = doacaoService.atualizarStatus("1", dto);
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("atualizarStatus - lanca StatusConflictException se update falhar")
    void atualizarStatus_conflito() {
        DoacaoModel model = DoacaoModel.builder().id("1").status(DoacaoStatus.DISPONIVEL).build();
        AtualizarStatusDTO dto = new AtualizarStatusDTO(DoacaoStatus.RESERVADO, "");

        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model));
        when(doacaoRepository.updateStatusAtomico(eq("1"), eq(DoacaoStatus.DISPONIVEL), eq(DoacaoStatus.RESERVADO), any())).thenReturn(false);

        assertThrows(StatusConflictException.class, () -> doacaoService.atualizarStatus("1", dto));
    }

    @Test
    @DisplayName("validarTransicaoStatus - mesmo status lanca excecao")
    void validarTransicaoStatus_mesmoStatus() {
        DoacaoModel model = DoacaoModel.builder().id("1").status(DoacaoStatus.DISPONIVEL).build();
        AtualizarStatusDTO dto = new AtualizarStatusDTO(DoacaoStatus.DISPONIVEL, null);

        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model));
        assertThrows(RegraDeNegocioException.class, () -> doacaoService.atualizarStatus("1", dto));
    }

    @Test
    @DisplayName("validarTransicaoStatus - de DOADO lanca excecao")
    void validarTransicaoStatus_doado() {
        DoacaoModel model = DoacaoModel.builder().id("1").status(DoacaoStatus.DOADO).build();
        AtualizarStatusDTO dto = new AtualizarStatusDTO(DoacaoStatus.DISPONIVEL, null);

        when(doacaoRepository.findById("1")).thenReturn(Optional.of(model));
        assertThrows(RegraDeNegocioException.class, () -> doacaoService.atualizarStatus("1", dto));
    }
}