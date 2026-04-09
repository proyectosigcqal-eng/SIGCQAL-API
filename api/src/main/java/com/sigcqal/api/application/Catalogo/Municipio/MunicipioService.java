package com.sigcqal.api.application.Catalogo.Municipio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.Catalogo.Municipio.Model.Municipio;
import com.sigcqal.api.domain.Catalogo.Municipio.Port.MunicipioRepositoryPort;
import com.sigcqal.api.web.Catalogo.Municipio.Dto.MunicipioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MunicipioService {

private final MunicipioRepositoryPort repositoryPort;

    public List<MunicipioDTO> obtenerMunicipios() {
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
    

