package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Port.RecursoRevisionRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model.SentenciaEjecutoria;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port.SentenciaEjecutoriaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Mapper.SentenciaEjecutoriaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarSentenciaEjecutoriaService implements GuardarSentenciaEjecutoriaUseCase {

    private static final String HITO = "Sentencia ejecutoria";
    private static final String PREDECESOR_SENTENCIA = "Sentencia dictada";
    private static final String PREDECESOR_RECURSO = "Recurso de revisión";

    private final SentenciaEjecutoriaRepositoryPort repositoryPort;
    private final SentenciaDictadaRepositoryPort sentenciaDictadaPort;
    private final RecursoRevisionRepositoryPort recursoRevisionPort;
    private final SentenciaEjecutoriaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    @Transactional
    public SentenciaEjecutoriaResponseDTO ejecutar(SentenciaEjecutoriaRequestDTO request) {
        validarPredecesorSentencia(request.getIdSentencia());
        validarPredecesorRecursoOpcional(request.getIdRecursoRevision());

        SentenciaEjecutoria sentenciaEjecutoria = SentenciaEjecutoria.builder()
                .idSentencia(request.getIdSentencia())
                .idRecursoRevision(request.getIdRecursoRevision())
                .numeroOficioEjecutoria(request.getNumeroOficioEjecutoria())
                .fechaDeclaracionEjecutoria(request.getFechaDeclaracionEjecutoria())
                .requerimientoCumplimiento(request.getRequerimientoCumplimiento())
                .rutaPdfOficio(request.getRutaPdfOficio())
                .fechaRegistro(LocalDateTime.now())
                .build();

        SentenciaEjecutoria guardada = repositoryPort.save(sentenciaEjecutoria);
        SentenciaEjecutoriaResponseDTO response = mapper.toResponse(guardada);
        encabezadoResolver.paraSentenciaEjecutoria(guardada).ifPresent(response::setEncabezado);
        return response;
    }

    private void validarPredecesorSentencia(Integer idSentencia) {
        if (!sentenciaDictadaPort.existsById(idSentencia)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR_SENTENCIA, idSentencia);
        }
    }

    private void validarPredecesorRecursoOpcional(Integer idRecursoRevision) {
        if (idRecursoRevision != null && !recursoRevisionPort.existsById(idRecursoRevision)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR_RECURSO, idRecursoRevision);
        }
    }
}
