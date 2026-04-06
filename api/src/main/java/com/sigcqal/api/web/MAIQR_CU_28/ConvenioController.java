package com.sigcqal.api.web.MAIQR_CU_28;

import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioMunicipio;
import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioRepositoryPort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/convenios")
public class ConvenioController {

    private final ConvenioRepositoryPort repositoryPort;

    public ConvenioController(ConvenioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @GetMapping
    public List<ConvenioResponseDTO> listarParaAdmin() {
        return repositoryPort.ListAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ConvenioResponseDTO mapToResponse(ConvenioMunicipio dom) {
        ConvenioResponseDTO res = new ConvenioResponseDTO();
        res.setNombreMunicipio(dom.getNombreMunicipio());
     
        res.setVigente(dom.estaVigente()); 
        return res;
    }
}