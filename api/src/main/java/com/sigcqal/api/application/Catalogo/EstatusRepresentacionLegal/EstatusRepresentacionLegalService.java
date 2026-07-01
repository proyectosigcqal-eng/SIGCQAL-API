package com.sigcqal.api.application.Catalogo.EstatusRepresentacionLegal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Model.EstatusRepresentacionLegal;
import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Port.EstatusRepresentacionLegalRepositoryPort;
import com.sigcqal.api.web.Catalogo.EstatusRepresentacionLegal.Dto.EstatusRepresentacionLegalDTO;

@Service
public class EstatusRepresentacionLegalService {

    private final EstatusRepresentacionLegalRepositoryPort repositoryPort;

    public EstatusRepresentacionLegalService(EstatusRepresentacionLegalRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<EstatusRepresentacionLegalDTO> listar() {
        List<EstatusRepresentacionLegal> lista = repositoryPort.findAll();
        return lista.stream()
                .map(this::toDTO)
                .toList();
    }

    private EstatusRepresentacionLegalDTO toDTO(EstatusRepresentacionLegal domain) {
        EstatusRepresentacionLegalDTO dto = new EstatusRepresentacionLegalDTO();
        dto.setId(domain.getId());
        dto.setNombre(domain.getNombre());
        return dto;
    }
}
