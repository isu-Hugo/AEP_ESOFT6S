package edu.unicesumar.aep.configuration;

import edu.unicesumar.aep.model.*;
import edu.unicesumar.aep.repository.DoacaoRepository;
import edu.unicesumar.aep.repository.PontoColetaRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;

@Configuration
public class DevDataConfiguration {

    @Bean
    CommandLineRunner loadDevData(DoacaoRepository doacaoRepository, PontoColetaRepository pontoColetaRepository) {
        return args -> {
            if (pontoColetaRepository.count() == 0) {
                PontoColetaModel ponto = PontoColetaModel.builder()
                        .id(new ObjectId().toHexString())
                        .nome("Prefeitura de Maringá")
                        .chave("pr|maringa|prefeitura de maringa")
                        .endereco(Endereco.builder()
                                .cidade("Maringá")
                                .uf("PR")
                                .logradouro("Av. Duque de Caxias")
                                .numero("359")
                                .bairro("Zona 01")
                                .complemento("Paço Municipal")
                                .build())
                        .build();

                PontoColetaModel pontoSalvo = pontoColetaRepository.save(ponto);

                if (doacaoRepository.count() == 0) {
                    DoacaoModel doacao = DoacaoModel.builder()
                            .id(new ObjectId().toHexString())
                            .itemDoacao("Camiseta de Algodão")
                            .obs("Doação inicial de demonstração")
                            .status(DoacaoStatus.DISPONIVEL)
                            .estadoUf("PR")
                            .cidade("Maringá")
                            .categoriaDoacao(CategoriaDoacao.ROUPA)
                            .tamanho("M")
                            .estadoConservacao(EstadoConservacao.USADO)
                            .pontoColetaId(pontoSalvo.getId())
                            .historicoStatus(List.of(
                                    HistoricoStatus.builder()
                                            .status(DoacaoStatus.DISPONIVEL)
                                            .dataHora(Instant.now())
                                            .observacao("Carga inicial do sistema (Seed)")
                                            .build()
                            ))
                            .build();

                    doacaoRepository.save(doacao);
                }
            }
        };
    }
}