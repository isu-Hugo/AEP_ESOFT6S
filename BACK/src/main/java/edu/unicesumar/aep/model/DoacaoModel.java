package edu.unicesumar.aep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "doacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoacaoModel {

    @Id
    private String id;
    private String nome_doador;
    private String itemDoacao;
    private String obs;
    private DOACAO_STATUS status;
    private String estado_uf;
    private String cidade;

    enum DOACAO_STATUS{
        DISPONIVEL,
        DOADO,
        RESERVADO
    }

}
