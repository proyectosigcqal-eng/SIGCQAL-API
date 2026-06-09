package com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Adapter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.AsesorDisponible;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Port.TurnadoPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TurnadoAdapter implements TurnadoPort {

    private final AsesorTurnadoRepository     asesorRepo;
    private final BitacoraAsignacionRepository bitacoraRepo;
    private final ExpedienteTurnadoRepository  expedienteRepo;

    @Override
    public List<AsesorDisponible> findAsesoresDisponibles() {
        return asesorRepo.findAsesoresOrdenadosRaw()
                .stream()
                .map(row -> AsesorDisponible.builder()
                        .idAsesor(((Number) row[0]).longValue())
                        .nombreCompleto(row[1] != null ? row[1].toString() : "")
                        .cargaActual(row[2] != null ? ((Number) row[2]).intValue() : 0)
                        .ultimaAsignacion(row[3] != null
                                ? toLocalDateTime(row[3]) : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void asignarAsesor(Integer idExpediente, Long idAsesor,
                               Long idAsesorAnterior, Integer idUsuario,
                               String ip, String motivo) {
        // 1. Actualizar el expediente
        expedienteRepo.actualizarAsesor(idExpediente, idAsesor);

        // 2. Registrar en bitácora
        bitacoraRepo.insertarBitacora(
                idExpediente, idAsesorAnterior, idAsesor,
                idUsuario, LocalDateTime.now(), ip, motivo);
    }

    @Override
    public void actualizarCargaAsesor(Long idAsesor) {
        asesorRepo.incrementarCarga(idAsesor);
    }

    private LocalDateTime toLocalDateTime(Object raw) {
        if (raw instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
        return null;
    }
}
