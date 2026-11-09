package edu.unicesumar.aep.controller;

import edu.unicesumar.aep.dto.AtualizarStatusDTO;
import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.dto.ResponseDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoStatus;
import edu.unicesumar.aep.model.HistoricoStatus;
import edu.unicesumar.aep.service.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @GetMapping("/doacoes")
    public ResponseEntity<List<ResponseDoacaoDTO>> findAll(
            @RequestParam(value = "status", required = false) DoacaoStatus status,
            @RequestParam(value = "ponto_coleta_id", required = false) String pontoColetaId
    ) {
        List<ResponseDoacaoDTO> response = this.doacaoService.findAll(status, pontoColetaId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/doacao")
    public ResponseEntity<ResponseDoacaoDTO> save(@RequestBody RequestDoacaoDTO dto) {
        ResponseDoacaoDTO response = this.doacaoService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/doacao/{id}")
    public ResponseEntity<ResponseDoacaoDTO> findById(@PathVariable("id") String id) {
        ResponseDoacaoDTO response = this.doacaoService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/doacao/{id}/status")
    public ResponseEntity<ResponseDoacaoDTO> atualizarStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody AtualizarStatusDTO dto
    ) {
        ResponseDoacaoDTO response = this.doacaoService.atualizarStatus(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doacao/{id}/historico")
    public ResponseEntity<List<HistoricoStatus>> findHistorico(@PathVariable("id") String id) {
        List<HistoricoStatus> response = this.doacaoService.findHistorico(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pontos-coleta/{id}/doacoes")
    public ResponseEntity<List<ResponseDoacaoDTO>> findDoacoesPorPonto(@PathVariable("id") String id) {
        List<ResponseDoacaoDTO> response = this.doacaoService.findByPontoColeta(id);
        return ResponseEntity.ok(response);
    }
}