package com.sigcqal.api.application.ModuloAreaSustantiva.Turnado;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.AsesorDisponible;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.ResultadoTurnado;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Port.TurnadoPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Repository.ExpedienteTurnadoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TurnadoService {

    private final TurnadoPort               turnadoPort;
    private final ExpedienteTurnadoRepository expedienteRepo;
@Transactional
public ResultadoTurnado turnarExpediente(String folio,
                                          Integer idUsuario,
                                          String ip) {
    // ← Usa List<Object[]> y toma el primer elemento
    List<Object[]> resultados = expedienteRepo.findDatosByFolio(folio);

    if (resultados == null || resultados.isEmpty()) {
        throw new InvalidRequestException(
                "Expediente no encontrado con folio: " + folio);
    }

    Object[] datos = resultados.get(0);

    Long    asesorActual = datos[0] != null ? ((Number) datos[0]).longValue() : null;
    Integer idExpediente = datos[1] != null ? ((Number) datos[1]).intValue()  : null;
    Boolean bloqueado    = datos[3] != null && Boolean.parseBoolean(datos[3].toString());

    if (Boolean.TRUE.equals(bloqueado)) {
        throw new InvalidRequestException(
                "El expediente " + folio + " está bloqueado y no puede turnarse.");
    }

    List<AsesorDisponible> asesores = turnadoPort.findAsesoresDisponibles();
    if (asesores.isEmpty()) {
        throw new InvalidRequestException(
                "No hay asesores disponibles para el turnado.");
    }

    AsesorDisponible asesorAsignado = asesores.get(0);

    turnadoPort.asignarAsesor(
            idExpediente,
            asesorAsignado.getIdAsesor(),
            asesorActual,
            idUsuario,
            ip,
            "Turnado automático Round Robin");

    turnadoPort.actualizarCargaAsesor(asesorAsignado.getIdAsesor());

    log.info("[Turnado] Expediente {} → Asesor {} (id={})",
            folio, asesorAsignado.getNombreCompleto(),
            asesorAsignado.getIdAsesor());

    return ResultadoTurnado.builder()
            .folioExpediente(folio)
            .idAsesorAsignado(asesorAsignado.getIdAsesor())
            .nombreAsesor(asesorAsignado.getNombreCompleto())
            .fechaAsignacion(LocalDateTime.now())
            .idAsesorAnterior(asesorActual)
            .build();
}

public ResultadoTurnado turnarAutomatico(String folio) {
    return turnarExpediente(folio, null, "SISTEMA");
}
}