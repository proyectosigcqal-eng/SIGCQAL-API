package com.sigcqal.api.web.Catalogo.Municipio.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigcqal.api.domain.Catalogo.Municipio.Model.Municipio;
import com.sigcqal.api.domain.Catalogo.Municipio.Port.MunicipioRepositoryPort;
import com.sigcqal.api.web.Catalogo.Municipio.Dto.MunicipioDTO;

@RestController
@RequestMapping("/catalogos/municipios")
public class MunicipioController {

     private final MunicipioRepositoryPort repositoryPort;

    public MunicipioController(MunicipioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @GetMapping
    public List<MunicipioDTO> listarParaAdmin() {
        return repositoryPort.ListAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MunicipioDTO mapToResponse(Municipio dom) {
        MunicipioDTO res = new MunicipioDTO();
        res.setNombreMunicipio(dom.getNombreMunicipio());
        res.setNombreEstado(dom.getEstado().getNombreEstado());
        return res;
    }
    
}
