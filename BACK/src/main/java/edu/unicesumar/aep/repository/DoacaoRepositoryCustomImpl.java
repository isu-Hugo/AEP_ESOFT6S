package edu.unicesumar.aep.repository;

import com.mongodb.client.result.UpdateResult;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoacaoRepositoryCustomImpl implements DoacaoRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean updateStatusAtomico(String id, DoacaoStatus statusAtual, DoacaoStatus novoStatus, HistoricoStatus novaEntrada) {
        Query query = new Query(Criteria.where("_id").is(id).and("status").is(statusAtual));

        Update update = new Update()
                .set("status", novoStatus)
                .push("historico_status", novaEntrada);

        UpdateResult result = mongoTemplate.updateFirst(query, update, DoacaoModel.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public List<DoacaoModel> findByFiltros(DoacaoStatus status, String pontoColetaId) {
        Query query = new Query();
        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        if (pontoColetaId != null && !pontoColetaId.isBlank()) {
            query.addCriteria(Criteria.where("ponto_coleta_id").is(pontoColetaId));
        }
        return mongoTemplate.find(query, DoacaoModel.class);
    }
}