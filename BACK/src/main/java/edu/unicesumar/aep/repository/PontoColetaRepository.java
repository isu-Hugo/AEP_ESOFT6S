package edu.unicesumar.aep.repository;

import edu.unicesumar.aep.model.PontoColetaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PontoColetaRepository extends MongoRepository<PontoColetaModel, String> {
    Optional<PontoColetaModel> findByChave(String chave);
}