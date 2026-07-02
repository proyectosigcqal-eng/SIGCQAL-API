package com.sigcqal.api.application.ModuloAreaSustantiva.BandejaRepresentacionLegal;

import com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Repository.DetalleIrlRepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto.DetalleIrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleIrlService {

    private final DetalleIrlRepository repository;

    public DetalleIrlResponseDto obtenerDetalle(String folio) {
        List<Object[]> rows = repository.findDetalleByFolio(folio);

        if (rows == null || rows.isEmpty()) {
            return DetalleIrlResponseDto.builder().build();
        }

        Object[] r = rows.get(0);
        return DetalleIrlResponseDto.builder()
            // Audiencia Espera
            .numeroOficioAdmision(str(r[0]))
            .fechaNotificacionOficio(str(r[1]))
            .fechaHoraAudienciaProg(str(r[2]))
            .observacionesEspera(str(r[3]))
            .rutaPdfOficioEspera(str(r[4]))
            // Audiencia Celebrada
            .fechaHoraCelebracion(str(r[5]))
            .numeroOficioActa(str(r[6]))
            .salaOModalidad(str(r[7]))
            .resultadoAudiencia(str(r[8]))
            .asistioAutoridad(r[9] != null ? Boolean.parseBoolean(r[9].toString()) : null)
            .rutaPdfOficioCelebrada(str(r[10]))
            // Sentencia Dictada
            .fechaDictado(str(r[11]))
            .fechaNotificacionSentencia(str(r[12]))
            .sentidoFallo(str(r[13]))
            .puntosResolutivos(str(r[14]))
            .numeroOficioSentencia(str(r[15]))
            .rutaArchivoSentencia(str(r[16]))
            .rutaPdfOficioSentencia(str(r[17]))
            // Sentencia Ejecutoria
            .numeroOficioEjecutoria(str(r[18]))
            .fechaDeclaracionEjecutoria(str(r[19]))
            .requerimientoCumplimiento(str(r[20]))
            .rutaPdfOficioEjecutoria(str(r[21]))
            // Notificación Cumplida
            .numeroOficioCumplimiento(str(r[22]))
            .numeroOficioArchivo(str(r[23]))
            .fechaNotificacionArchivo(str(r[24]))
            .observacionesFinales(str(r[25]))
            .rutaPdfOficioCumplimiento(str(r[26]))
            .build();
    }

    private String str(Object v) {
        return v != null ? v.toString() : null;
    }
}