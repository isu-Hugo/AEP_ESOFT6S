package edu.unicesumar.aep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
import edu.unicesumar.aep.repository.DoacaoRepositoryCustomImpl;

@ExtendWith(MockitoExtension.class)
public class DoacaoRepositoryCustomImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private DoacaoRepositoryCustomImpl repository;

    @Test
    @DisplayName("updateStatusAtomico deve retornar true quando modifica")
    void updateStatusAtomico_sucesso() {
        UpdateResult result = mock(UpdateResult.class);
        when(result.getModifiedCount()).thenReturn(1L);
        when(mongoTemplate.updateFirst(any(Query.class), any(Update.class), eq(DoacaoModel.class))).thenReturn(result);

        HistoricoStatus novaEntrada = HistoricoStatus.builder().build();
        boolean sucesso = repository.updateStatusAtomico("1", DoacaoStatus.DISPONIVEL, DoacaoStatus.RESERVADO, novaEntrada);

        assertTrue(sucesso);
    }

    @Test
    @DisplayName("findByFiltros com status e pontoId")
    void findByFiltros_comFiltros() {
        DoacaoModel model = new DoacaoModel();
        when(mongoTemplate.find(any(Query.class), eq(DoacaoModel.class))).thenReturn(List.of(model));

        List<DoacaoModel> resultado = repository.findByFiltros(DoacaoStatus.DISPONIVEL, "p1");

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("findByFiltros sem filtros")
    void findByFiltros_semFiltros() {
        DoacaoModel model = new DoacaoModel();
        when(mongoTemplate.find(any(Query.class), eq(DoacaoModel.class))).thenReturn(List.of(model));

        List<DoacaoModel> resultado = repository.findByFiltros(null, null);

        assertEquals(1, resultado.size());
    }
}