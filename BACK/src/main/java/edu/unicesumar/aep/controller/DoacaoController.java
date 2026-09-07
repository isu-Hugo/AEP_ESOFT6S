package edu.unicesumar.aep.controller;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.service.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @GetMapping("/doacoes")
    public ResponseEntity<List<RequestDoacaoDTO>> findAll(){
        List<RequestDoacaoDTO> response = this.doacaoService.findAll();
        return ResponseEntity.ok(response);
    }
}
