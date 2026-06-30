package com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model.RecursoRevision;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Port.RecursoRevisionRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Mapper.RecursoRevisionMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarRecursoRevisionService implements GuardarRecursoRevisionUseCase {

    private static final String HITO = "Recurso de revisión";
    private static final String PREDECESOR = "Sentencia dictada";

    private final RecursoRevisionRepositoryPort repositoryPort;
    private final SentenciaDictadaRepositoryPort sentenciaDictadaPort;
    private final RecursoRevisionMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    @Transactional
    public RecursoRevisionResponseDTO ejecutar(RecursoRevisionRequestDTO request) {
        validarPredecesor(request.getIdSentencia());

        RecursoRevision recursoRevision = RecursoRevision.builder()
                .idSentencia(request.getIdSentencia())
                .numeroOficioInterposicion(request.getNumeroOficioInterposicion())
                .numeroExpedienteRevision(request.getNumeroExpedienteRevision())
                .tribunalColegiadoAsig(request.getTribunalColegiadoAsig())
                .fechaInterposicion(request.getFechaInterposicion())
                .observacionesSeguimiento(request.getObservacionesSeguimiento())
                .rutaPdfOficio(request.getRutaPdfOficio())
                .fechaRegistro(LocalDateTime.now())
                .build();

        RecursoRevision guardado = repositoryPort.save(recursoRevision);
        RecursoRevisionResponseDTO response = mapper.toResponse(guardado);
        encabezadoResolver.paraRecursoRevision(guardado).ifPresent(response::setEncabezado);
        return response;
    }

    private void validarPredecesor(Integer idSentencia) {
        if (!sentenciaDictadaPort.existsById(idSentencia)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR, idSentencia);
        }
    }
}
