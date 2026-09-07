package edu.unicesumar.aep.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "doacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoacaoModel {

    @Id
    private String id;
    @Field("nome_doador")
    private String nomeDoador;
    @Field("item_doacao")
    private String itemDoacao;
    private String obs;
    private DoacaoStatus status;
    @Field("estado_uf")
    private String estadoUf;
    private String cidade;


}
