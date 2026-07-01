package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model.AudienciaEspera;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port.AudienciaEsperaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DemandaAmparo.Port.DemandaAmparoRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Mapper.AudienciaEsperaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarAudienciaEsperaService implements GuardarAudienciaEsperaUseCase {

    private static final String HITO = "Audiencia en espera";
    private static final String PREDECESOR = "Demanda de amparo";

    private final AudienciaEsperaRepositoryPort repositoryPort;
    private final DemandaAmparoRepositoryPort demandaAmparoPort;
    private final AudienciaEsperaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    @Transactional
    public AudienciaEsperaResponseDTO ejecutar(AudienciaEsperaRequestDTO request) {
        validarPredecesor(request.getIdDemandaAmparo());

        AudienciaEspera audienciaEspera = AudienciaEspera.builder()
                .idDemandaAmparo(request.getIdDemandaAmparo())
                .numeroOficioAdmision(request.getNumeroOficioAdmision())
                .fechaNotificacionOficio(request.getFechaNotificacionOficio())
                .fechaHoraAudienciaProg(request.getFechaHoraAudienciaProg())
                .observaciones(request.getObservaciones())
                .rutaPdfOficio(request.getRutaPdfOficio())
                .fechaRegistro(LocalDateTime.now())
                .build();

        AudienciaEspera guardada = repositoryPort.save(audienciaEspera);
        AudienciaEsperaResponseDTO response = mapper.toResponse(guardada);
        encabezadoResolver.paraAudienciaEspera(guardada).ifPresent(response::setEncabezado);
        return response;
    }

    private void validarPredecesor(Integer idDemandaAmparo) {
        if (!demandaAmparoPort.existsById(idDemandaAmparo)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR, idDemandaAmparo);
        }
    }
}
