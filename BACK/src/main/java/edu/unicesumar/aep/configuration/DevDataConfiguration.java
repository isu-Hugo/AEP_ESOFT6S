package edu.unicesumar.aep.configuration;

import edu.unicesumar.aep.model.CategoriaDoacao;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.EstadoConservacao;
import edu.unicesumar.aep.repository.DoacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.List;

@Configuration
public class DevDataConfiguration {

    @Bean
    CommandLineRunner loadDevData(DoacaoRepository repository){
        return args -> exampleData().stream()
                .filter(d -> !repository.existsById(d.getId()))
                .forEach(repository::save);

    }

    private List<DoacaoModel> exampleData(){
        return List.of(
                new DoacaoModel(
                        "first",
                        "Camiseta",
                        "",
                        DoacaoStatus.DISPONIVEL,
                        "PR",
                        "MARINGA",
                        CategoriaDoacao.ROUPA,
                        "M",
                        EstadoConservacao.USADO,
                        "Prefeitura"
                )
        );
    }
}
