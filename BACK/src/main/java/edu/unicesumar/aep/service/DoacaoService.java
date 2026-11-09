package edu.unicesumar.aep.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private PontoColetaService pontoColetaService;

    @Autowired
    private DoacaoMapper mapper;

    // 1. Listagem com suporte a filtros opcionais por status e ponto de coleta
    public List<ResponseDoacaoDTO> findAll(DoacaoStatus status, String pontoColetaId) {
        List<DoacaoModel> lista;
        if (status != null || (pontoColetaId != null && !pontoColetaId.isBlank())) {
            lista = doacaoRepository.findByFiltros(status, pontoColetaId);
        } else {
            lista = doacaoRepository.findAll();
        }

        return lista.stream()
                .map(this::enriquecerComPonto)
                .toList();
    }

    // 2. Salvar com resolução inteligente de ponto de coleta
    public ResponseDoacaoDTO save(RequestDoacaoDTO dto) {
        PontoColetaModel ponto = null;
        String pontoId = dto.ponto_coleta_id();

        // Se veio o nome em texto, busca ou cria na nova coleção
        if (dto.ponto_coleta() != null && !dto.ponto_coleta().isBlank()) {
            ponto = pontoColetaService.obterOuCriarPonto(dto.estado_uf(), dto.cidade(), dto.ponto_coleta());
            if (ponto != null) {
                pontoId = ponto.getId();
            }
        } else if (pontoId != null && !pontoId.isBlank()) {
            ponto = pontoColetaService.findById(pontoId).orElse(null);
        }

        DoacaoModel model = mapper.toModel(dto, pontoId);
        DoacaoModel salvo = doacaoRepository.save(model);
        return mapper.toResponse(salvo, ponto);
    }

    // 3. Buscar doação por ID
    public ResponseDoacaoDTO findById(String id) {
        DoacaoModel model = doacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Doação não encontrada para o ID: " + id));
        return enriquecerComPonto(model);
    }

    // 4. Buscar apenas o histórico da doação (consulta ao subdocumento)
    public List<HistoricoStatus> findHistorico(String id) {
        DoacaoModel model = doacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Doação não encontrada para o ID: " + id));
        return model.getHistoricoStatus();
    }

    // 5. Buscar todas as doações de um determinado ponto de coleta (relação 1:N)
    public List<ResponseDoacaoDTO> findByPontoColeta(String pontoColetaId) {
        pontoColetaService.findById(pontoColetaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de coleta não encontrado: " + pontoColetaId));

        return doacaoRepository.findByPontoColetaId(pontoColetaId).stream()
                .map(this::enriquecerComPonto)
                .toList();
    }

    // 6. Atualização Atômica de Status com regras de negócio
    public ResponseDoacaoDTO atualizarStatus(String id, AtualizarStatusDTO dto) {
        DoacaoModel atual = doacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Doação não encontrada para o ID: " + id));

        DoacaoStatus statusAtual = atual.getStatus();
        DoacaoStatus novoStatus = dto.status();

        validarTransicaoStatus(statusAtual, novoStatus);

        String obs = dto.observacao() != null && !dto.observacao().isBlank()
                ? dto.observacao()
                : "Status alterado de " + statusAtual + " para " + novoStatus;

        HistoricoStatus novaEntrada = HistoricoStatus.builder()
                .status(novoStatus)
                .dataHora(Instant.now())
                .observacao(obs)
                .build();

        boolean atualizado = doacaoRepository.updateStatusAtomico(id, statusAtual, novoStatus, novaEntrada);
        if (!atualizado) {
            throw new StatusConflictException("Conflito ao atualizar status: a doação foi alterada simultaneamente por outro processo.");
        }

        DoacaoModel atualizadoModel = doacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Doação não encontrada após atualização."));

        return enriquecerComPonto(atualizadoModel);
    }

    // Regras de negócio da esteira de status
    private void validarTransicaoStatus(DoacaoStatus atual, DoacaoStatus novo) {
        if (atual == novo) {
            throw new RegraDeNegocioException("A doação já se encontra no status " + novo);
        }

        if (atual == DoacaoStatus.DOADO) {
            throw new RegraDeNegocioException("Doação com status DOADO é final e não pode ser reaberta.");
        }

        if (atual == DoacaoStatus.DISPONIVEL && (novo != DoacaoStatus.RESERVADO && novo != DoacaoStatus.DOADO)) {
            throw new RegraDeNegocioException("Transição inválida de DISPONIVEL para " + novo);
        }

        if (atual == DoacaoStatus.RESERVADO && (novo != DoacaoStatus.DOADO && novo != DoacaoStatus.DISPONIVEL)) {
            throw new RegraDeNegocioException("Transição inválida de RESERVADO para " + novo);
        }
    }

    // Método auxiliar para buscar o ponto no banco e anexar na resposta
    private ResponseDoacaoDTO enriquecerComPonto(DoacaoModel model) {
        PontoColetaModel ponto = null;
        if (model.getPontoColetaId() != null && !model.getPontoColetaId().isBlank()) {
            ponto = pontoColetaService.findById(model.getPontoColetaId()).orElse(null);
        }
        return mapper.toResponse(model, ponto);
    }
}