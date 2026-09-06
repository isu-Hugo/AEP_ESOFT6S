package edu.unicesumar.aep.repository;

import edu.unicesumar.aep.model.DoacaoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoacaoRepository extends MongoRepository<DoacaoModel, String> {
}
