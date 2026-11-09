package edu.unicesumar.aep.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "doacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoModel {

    @Id
    private String id;
    @Field("item_doacao")
    private String itemDoacao;
    private String obs;
    @Indexed
    private DoacaoStatus status;
    @Field("estado_uf")
    private String estadoUf;
    private String cidade;
    @Field("categoria_doacao")
    private CategoriaDoacao categoriaDoacao;
    private String tamanho;
    @Field("estado_conservacao")
    private EstadoConservacao estadoConservacao;
    @Indexed
    @Field("ponto_coleta_id")
    private String pontoColetaId;

    @Builder.Default
    @Field("historico_status")
    private List<HistoricoStatus> historicoStatus = new ArrayList<>();
}