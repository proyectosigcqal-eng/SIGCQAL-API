package com.sigcqal.api.application.Catalogo.CatEstatusSustantiva;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Model.CatEstatusSustantiva;
import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Port.CatEstatusSustantivaRepositoryPort;
import com.sigcqal.api.web.Catalogo.CatEstatusSustantiva.Dto.CatEstatusSustantivaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatEstatusSustantivaService {
    private final CatEstatusSustantivaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "catEstatusSustantivaAll", key = "'all'")
    public List<CatEstatusSustantivaDTO> obtenerCatEstatusSustantiva() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "catEstatusSustantivaById", key = "#id")
    public CatEstatusSustantivaDTO obtenerCatEstatusSustantiva(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        CatEstatusSustantiva cat = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("CatEstatusSustantiva", id));
        return mapToResponse(cat);
    }

    private CatEstatusSustantivaDTO mapToResponse(CatEstatusSustantiva dom) {
        CatEstatusSustantivaDTO dto = new CatEstatusSustantivaDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
