package edu.unicesumar.aep.service;

import edu.unicesumar.aep.model.Endereco;
import edu.unicesumar.aep.model.PontoColetaModel;
import edu.unicesumar.aep.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class PontoColetaService {

    @Autowired
    private PontoColetaRepository repository;

    public List<PontoColetaModel> findAll() {
        return repository.findAll();
    }

    public Optional<PontoColetaModel> findById(String id) {
        return repository.findById(id);
    }

    public PontoColetaModel obterOuCriarPonto(String uf, String cidade, String nomePonto) {
        if (nomePonto == null || nomePonto.trim().isBlank()) {
            return null;
        }

        String chave = normalizarChave(uf, cidade, nomePonto);

        return repository.findByChave(chave).orElseGet(() -> {
            Endereco endereco = Endereco.builder()
                    .cidade(cidade != null ? cidade.trim() : "")
                    .uf(uf != null ? uf.trim().toUpperCase() : "")
                    .build();

            PontoColetaModel novoPonto = PontoColetaModel.builder()
                    .nome(nomePonto.trim())
                    .chave(chave)
                    .endereco(endereco)
                    .build();

            return repository.save(novoPonto);
        });
    }

    public String normalizarChave(String uf, String cidade, String nome) {
        String parteUf = uf != null ? removerAcentos(uf.trim().toLowerCase()) : "";
        String parteCidade = cidade != null ? removerAcentos(cidade.trim().toLowerCase()) : "";
        String parteNome = nome != null ? removerAcentos(nome.trim().toLowerCase()) : "";
        return parteUf + "|" + parteCidade + "|" + parteNome;
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}