package edu.unicesumar.aep.service;

import edu.unicesumar.aep.dto.RequestDoacaoDTO;
import edu.unicesumar.aep.mapper.DoacaoMapper;
import edu.unicesumar.aep.model.DoacaoModel;
import edu.unicesumar.aep.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;
    @Autowired
    private DoacaoMapper mapper;

    public List<RequestDoacaoDTO> findAll(){
        return this.doacaoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
