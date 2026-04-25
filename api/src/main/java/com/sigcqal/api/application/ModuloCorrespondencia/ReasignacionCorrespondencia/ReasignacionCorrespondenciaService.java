package com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionCorrespondencia;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port.IReasignacionCorrespondenciaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionCorrespondencia.Mapper.ReasignacionCorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

@Service
public class ReasignacionCorrespondenciaService {

    @Autowired
    private IReasignacionCorrespondenciaPort repositoryPort;

    @Autowired
    private ReasignacionCorrespondenciaMapper mapper;

    public List<ReasignacionCorrespondenciaResponseDTO> pendientes() {
        return repositoryPort.findPendientes()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

