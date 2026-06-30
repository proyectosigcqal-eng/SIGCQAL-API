package com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionSentenciaCumplida;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model.NotificacionSentenciaCumplida;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Port.NotificacionSentenciaCumplidaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port.SentenciaEjecutoriaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Mapper.NotificacionSentenciaCumplidaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto.NotificacionSentenciaCumplidaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarNotificacionSentenciaCumplidaService implements GuardarNotificacionSentenciaCumplidaUseCase {

    private static final String HITO = "Notificación de sentencia cumplida";
    private static final String PREDECESOR = "Sentencia ejecutoria";

    private final NotificacionSentenciaCumplidaRepositoryPort repositoryPort;
    private final SentenciaEjecutoriaRepositoryPort sentenciaEjecutoriaPort;
    private final NotificacionSentenciaCumplidaMapper mapper;

    @Override
    @Transactional
    public NotificacionSentenciaCumplidaResponseDTO ejecutar(NotificacionSentenciaCumplidaRequestDTO request) {
        validarPredecesor(request.getIdSentenciaEjecutoria());

        NotificacionSentenciaCumplida notificacion = NotificacionSentenciaCumplida.builder()
                .idSentenciaEjecutoria(request.getIdSentenciaEjecutoria())
                .numeroOficioCumplimiento(request.getNumeroOficioCumplimiento())
                .numeroOficioArchivo(request.getNumeroOficioArchivo())
                .fechaNotificacionArchivo(request.getFechaNotificacionArchivo())
                .observacionesFinales(request.getObservacionesFinales())
                .fechaRegistro(LocalDateTime.now())
                .build();

        return mapper.toResponse(repositoryPort.save(notificacion));
    }

    private void validarPredecesor(Integer idSentenciaEjecutoria) {
        if (!sentenciaEjecutoriaPort.existsById(idSentenciaEjecutoria)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR, idSentenciaEjecutoria);
        }
    }
}
