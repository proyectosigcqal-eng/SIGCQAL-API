package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model.AudienciaCelebrada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port.AudienciaEsperaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Mapper.AudienciaCelebradaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarAudienciaCelebradaService implements GuardarAudienciaCelebradaUseCase {

    private static final String HITO = "Audiencia celebrada";
    private static final String PREDECESOR = "Audiencia en espera";

    private final AudienciaCelebradaRepositoryPort repositoryPort;
    private final AudienciaEsperaRepositoryPort audienciaEsperaPort;
    private final AudienciaCelebradaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    @Transactional
    public AudienciaCelebradaResponseDTO ejecutar(AudienciaCelebradaRequestDTO request) {
        validarPredecesor(request.getIdAudienciaEspera());

        AudienciaCelebrada audienciaCelebrada = AudienciaCelebrada.builder()
                .idAudienciaEspera(request.getIdAudienciaEspera())
                .fechaHoraCelebracion(request.getFechaHoraCelebracion())
                .numeroOficioActa(request.getNumeroOficioActa())
                .salaOModalidad(request.getSalaOModalidad())
                .resultadoAudiencia(request.getResultadoAudiencia())
                .asistioAutoridad(request.getAsistioAutoridad())
                .rutaPdfOficio(request.getRutaPdfOficio())
                .fechaRegistro(LocalDateTime.now())
                .build();

        AudienciaCelebrada guardada = repositoryPort.save(audienciaCelebrada);
        AudienciaCelebradaResponseDTO response = mapper.toResponse(guardada);
        encabezadoResolver.paraAudienciaCelebrada(guardada).ifPresent(response::setEncabezado);
        return response;
    }

    private void validarPredecesor(Integer idAudienciaEspera) {
        if (!audienciaEsperaPort.existsById(idAudienciaEspera)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR, idAudienciaEspera);
        }
    }
}
