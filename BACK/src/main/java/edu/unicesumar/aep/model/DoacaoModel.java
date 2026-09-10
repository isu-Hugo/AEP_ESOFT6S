package edu.unicesumar.aep.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "doacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoModel {

    @Id
    private String id;
//    @Field("nome_doador")
//    private String nomeDoador;
    @Field("item_doacao")
    private String itemDoacao;
    private String obs;
    private DoacaoStatus status;
    @Field("estado_uf")
    private String estadoUf;
    private String cidade;

    @Field("categoria_doacao")
    private CategoriaDoacao categoriaDoacao;
    private String tamanho;
    @Field("estado_conservacao")
    private EstadoConservacao estadoConservacao;
    @Field("ponto_coleta")
    private String pontoColeta;


}
