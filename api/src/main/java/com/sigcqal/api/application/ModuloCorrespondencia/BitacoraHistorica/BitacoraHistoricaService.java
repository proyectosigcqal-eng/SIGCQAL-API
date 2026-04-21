package com.sigcqal.api.application.ModuloCorrespondencia.BitacoraHistorica;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Port.BitacoraHistoricaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Mapper.BitacoraHistoricaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.BitacoraHistorica.Dto.BitacoraHistoricaResponseDTO;

@Service
public class BitacoraHistoricaService {
    @Autowired
    private BitacoraHistoricaRepositoryPort repositoryPort;

    @Autowired
    private BitacoraHistoricaMapper mapper;

    public List<BitacoraHistoricaResponseDTO> obtenerLineaDeTiempo(Long idCorrespondencia) {
        return repositoryPort.findByIdCorrespondencia(idCorrespondencia)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
