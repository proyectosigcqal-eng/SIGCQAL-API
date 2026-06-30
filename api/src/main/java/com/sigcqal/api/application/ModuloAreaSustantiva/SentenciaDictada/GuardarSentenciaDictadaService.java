package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.exception.HitoSecuenciaVioladaException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model.SentenciaDictada;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Mapper.SentenciaDictadaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardarSentenciaDictadaService implements GuardarSentenciaDictadaUseCase {

    private static final String HITO = "Sentencia dictada";
    private static final String PREDECESOR = "Audiencia celebrada";

    private final SentenciaDictadaRepositoryPort repositoryPort;
    private final AudienciaCelebradaRepositoryPort audienciaCelebradaPort;
    private final SentenciaDictadaMapper mapper;

    @Override
    @Transactional
    public SentenciaDictadaResponseDTO ejecutar(SentenciaDictadaRequestDTO request) {
        validarPredecesor(request.getIdAudienciaCelebrada());

        SentenciaDictada sentenciaDictada = SentenciaDictada.builder()
                .idAudienciaCelebrada(request.getIdAudienciaCelebrada())
                .fechaDictado(request.getFechaDictado())
                .fechaNotificacionSentencia(request.getFechaNotificacionSentencia())
                .sentidoFallo(request.getSentidoFallo())
                .puntosResolutivos(request.getPuntosResolutivos())
                .numeroOficioSentencia(request.getNumeroOficioSentencia())
                .rutaArchivoSentencia(request.getRutaArchivoSentencia())
                .fechaRegistro(LocalDateTime.now())
                .build();

        return mapper.toResponse(repositoryPort.save(sentenciaDictada));
    }

    private void validarPredecesor(Integer idAudienciaCelebrada) {
        if (!audienciaCelebradaPort.existsById(idAudienciaCelebrada)) {
            throw new HitoSecuenciaVioladaException(HITO, PREDECESOR, idAudienciaCelebrada);
        }
    }
}
