package com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionSentenciaCumplida;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Port.NotificacionSentenciaCumplidaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Mapper.NotificacionSentenciaCumplidaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerNotificacionSentenciaCumplidaService implements ObtenerNotificacionSentenciaCumplidaUseCase {

    private final NotificacionSentenciaCumplidaRepositoryPort repositoryPort;
    private final NotificacionSentenciaCumplidaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    public List<NotificacionSentenciaCumplidaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(domain -> {
                    NotificacionSentenciaCumplidaResponseDTO response = mapper.toResponse(domain);
                    encabezadoResolver.paraNotificacionSentenciaCumplida(domain).ifPresent(response::setEncabezado);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
