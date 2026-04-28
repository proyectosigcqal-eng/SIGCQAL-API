package com.sigcqal.api.application.ModuloCorrespondencia.ReasignacionMemorandum;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionMemorandum.Port.IReasignacionMemorandumPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionMemorandum.Mapper.ReasignacionMemorandumMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionMemorandum.Dto.ReasignacionMemorandumResponseDTO;

@Service
public class ReasignacionMemorandumService {

    @Autowired
    private IReasignacionMemorandumPort reasignacionMemorandumPort;

    @Autowired
    private ReasignacionMemorandumMapper mapper;

    public List<ReasignacionMemorandumResponseDTO> pendientes() {
        return reasignacionMemorandumPort.findPendientes()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

