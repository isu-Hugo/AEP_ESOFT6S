package edu.unicesumar.aep.repository;

import edu.unicesumar.aep.model.DoacaoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoacaoRepository extends MongoRepository<DoacaoModel, String>, DoacaoRepositoryCustom {
    List<DoacaoModel> findByPontoColetaId(String pontoColetaId);
}