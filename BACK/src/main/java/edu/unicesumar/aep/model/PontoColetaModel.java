package edu.unicesumar.aep.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pontos_coleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PontoColetaModel {

    @Id
    private String id;
    private String nome;

    @Indexed(unique = true)
    private String chave; // Ex: "pr|maringa|igreja matriz"

    private Endereco endereco;
}