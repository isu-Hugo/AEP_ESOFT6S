package edu.unicesumar.aep.controller;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.service.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @GetMapping("/doacoes")
    public ResponseEntity<List<RequestDoacaoDTO>> findAll(){
        List<RequestDoacaoDTO> response = this.doacaoService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/doacao")
    public ResponseEntity<RequestDoacaoDTO> save(@RequestBody RequestDoacaoDTO dto){
        RequestDoacaoDTO response = this.doacaoService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/doacao/{id}")
    public ResponseEntity<RequestDoacaoDTO> findById(@PathVariable("id") String id){
        RequestDoacaoDTO response = this.doacaoService.findById(id);
        return ResponseEntity.ok(response);
    }
}
