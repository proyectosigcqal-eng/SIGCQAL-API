package com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model.AudienciaCelebrada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model.AudienciaEspera;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port.AudienciaEsperaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DemandaAmparo.Port.DemandaAmparoRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model.NotificacionSentenciaCumplida;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model.RecursoRevision;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model.SentenciaDictada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model.SentenciaEjecutoria;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port.SentenciaEjecutoriaRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.DemandaAmparo.Dto.EncabezadoHitoAmparoDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EncabezadoHitoAmparoResolver {

    private final DemandaAmparoRepositoryPort demandaAmparoPort;
    private final AudienciaEsperaRepositoryPort audienciaEsperaPort;
    private final AudienciaCelebradaRepositoryPort audienciaCelebradaPort;
    private final SentenciaDictadaRepositoryPort sentenciaDictadaPort;
    private final SentenciaEjecutoriaRepositoryPort sentenciaEjecutoriaPort;

    public Optional<EncabezadoHitoAmparoDto> paraAudienciaEspera(AudienciaEspera audienciaEspera) {
        return obtenerEncabezado(audienciaEspera.getIdDemandaAmparo());
    }

    public Optional<EncabezadoHitoAmparoDto> paraAudienciaCelebrada(AudienciaCelebrada audienciaCelebrada) {
        return audienciaEsperaPort.findById(audienciaCelebrada.getIdAudienciaEspera())
                .flatMap(this::paraAudienciaEspera);
    }

    public Optional<EncabezadoHitoAmparoDto> paraSentenciaDictada(SentenciaDictada sentenciaDictada) {
        return audienciaCelebradaPort.findById(sentenciaDictada.getIdAudienciaCelebrada())
                .flatMap(this::paraAudienciaCelebrada);
    }

    public Optional<EncabezadoHitoAmparoDto> paraRecursoRevision(RecursoRevision recursoRevision) {
        return sentenciaDictadaPort.findById(recursoRevision.getIdSentencia())
                .flatMap(this::paraSentenciaDictada);
    }

    public Optional<EncabezadoHitoAmparoDto> paraSentenciaEjecutoria(SentenciaEjecutoria sentenciaEjecutoria) {
        return sentenciaDictadaPort.findById(sentenciaEjecutoria.getIdSentencia())
                .flatMap(this::paraSentenciaDictada);
    }

    public Optional<EncabezadoHitoAmparoDto> paraNotificacionSentenciaCumplida(
            NotificacionSentenciaCumplida notificacion) {
        return sentenciaEjecutoriaPort.findById(notificacion.getIdSentenciaEjecutoria())
                .flatMap(this::paraSentenciaEjecutoria);
    }

    private Optional<EncabezadoHitoAmparoDto> obtenerEncabezado(Integer idDemandaAmparo) {
        if (idDemandaAmparo == null) {
            return Optional.empty();
        }
        return demandaAmparoPort.obtenerEncabezadoPorAmparoId(idDemandaAmparo.longValue());
    }
}
