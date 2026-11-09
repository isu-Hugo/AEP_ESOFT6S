package edu.unicesumar.aep.controller;

import edu.unicesumar.aep.exception.RecursoNaoEncontradoException;
import edu.unicesumar.aep.model.PontoColetaModel;
import edu.unicesumar.aep.service.PontoColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PontoColetaController {

    @Autowired
    private PontoColetaService pontoColetaService;

    @GetMapping("/pontos-coleta")
    public ResponseEntity<List<PontoColetaModel>> findAll() {
        return ResponseEntity.ok(pontoColetaService.findAll());
    }

    @GetMapping("/pontos-coleta/{id}")
    public ResponseEntity<PontoColetaModel> findById(@PathVariable("id") String id) {
        PontoColetaModel ponto = pontoColetaService.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto de coleta não encontrado: " + id));
        return ResponseEntity.ok(ponto);
    }
}