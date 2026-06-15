package com.sigcqal.api.application.ModuloAreaSustantiva.CierreAutomatico;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Model.CierreAutomatico;
import com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Port.CierreAutomaticoPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CierreAutomaticoService {

    private static final int DIAS_HABILES_PLAZO = 3;

    private final CierreAutomaticoPort cierrePort;
    private final DiaInhabilRepositoryPort diaInhabilPort;

    @Transactional
    public void verificarYCerrarVencidos() {
        List<CierreAutomatico> expedientes = cierrePort.findExpedientesEnPrevencion();

        if (expedientes.isEmpty()) {
            log.info("[CierreAutomatico] No hay expedientes en prevención.");
            return;
        }

        log.info("[CierreAutomatico] Verificando {} expedientes en prevención...",
                expedientes.size());

        LocalDate hoy = LocalDate.now();
        int cerrados = 0;

        for (CierreAutomatico exp : expedientes) {
            try {
                LocalDate inicio = exp.getFechaSolicitud();
                LocalDate hasta  = inicio.plusDays(30);

                List<DiaInhabil> inhabiles =
                        diaInhabilPort.findByRangoFechas(inicio, hasta);
                Set<LocalDate> fechasInhabiles = inhabiles.stream()
                        .map(DiaInhabil::getFecha)
                        .collect(Collectors.toSet());

                LocalDate fechaLimite =
                        sumarDiasHabiles(inicio, DIAS_HABILES_PLAZO, fechasInhabiles);

                boolean vencido = hoy.isAfter(fechaLimite);

                if (vencido) {
                    cierrePort.cerrarExpedienteNoPresentado(exp.getIdExpediente());
                    log.info("[CierreAutomatico] Expediente {} cerrado. " +
                             "Inicio: {} | Límite: {} | Hoy: {}",
                            exp.getFolioGobierno(), inicio, fechaLimite, hoy);
                    cerrados++;
                }
            } catch (Exception e) {
                log.error("[CierreAutomatico] Error al procesar expediente {}: {}",
                        exp.getFolioGobierno(), e.getMessage());
            }
        }

        log.info("[CierreAutomatico] Proceso terminado. {} expediente(s) cerrado(s).", cerrados);
    }

    // ── Reutiliza la misma lógica de PlazoPrevencionService ────────────
    private LocalDate sumarDiasHabiles(LocalDate desde, int dias,
                                        Set<LocalDate> inhabiles) {
        LocalDate fecha = desde;
        int contados = 0;
        while (contados < dias) {
            fecha = fecha.plusDays(1);
            if (esDiaHabil(fecha, inhabiles)) contados++;
        }
        return fecha;
    }

    private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> inhabiles) {
        return fecha.getDayOfWeek() != DayOfWeek.SATURDAY
                && fecha.getDayOfWeek() != DayOfWeek.SUNDAY
                && !inhabiles.contains(fecha);
    }
}