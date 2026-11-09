package edu.unicesumar.aep.repository;

import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;

import java.util.List;

public interface DoacaoRepositoryCustom {
    boolean updateStatusAtomico(String id, DoacaoStatus statusAtual, DoacaoStatus novoStatus, HistoricoStatus novaEntrada);
    List<DoacaoModel> findByFiltros(DoacaoStatus status, String pontoColetaId);
}