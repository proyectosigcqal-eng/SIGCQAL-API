package com.sigcqal.api.application.Catalogo.Asesor;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.web.Catalogo.Asesor.Dto.AsesorDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsesorService {
    private final AsesorRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "asesoresAll", key = "'all'")
    public List<AsesorDTO> obtenerAsesores() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    private AsesorDTO mapToResponse(Asesor domain) {
        AsesorDTO dto = new AsesorDTO();
        dto.setIdAsesor(domain.getIdAsesor());
        dto.setIdPersona(domain.getIdPersona());
        dto.setEspecialidad(domain.getEspecialidad());
        dto.setCargaActual(domain.getCargaActual());
        dto.setUltimaAsignacionAt(domain.getUltimaAsignacionAt() != null ? domain.getUltimaAsignacionAt().toString() : null);
        return dto;
    }
}
