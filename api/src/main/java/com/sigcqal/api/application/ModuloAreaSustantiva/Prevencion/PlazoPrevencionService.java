package com.sigcqal.api.application.ModuloAreaSustantiva.Prevencion;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.PlazoPrevencion.Model.PlazoPrevencion;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Repository.ExpedientePrevencionJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlazoPrevencionService {

    private static final int DIAS_HABILES_PLAZO = 3;

    private final DiaInhabilRepositoryPort diaInhabilPort;
    private final ExpedientePrevencionJpaRepository expedienteRepository;
    private final QuejaJPARepository quejaJpaRepository; // ← nuevo

    @Transactional
    public PlazoPrevencion calcularPlazo(String folio) {

        boolean estaBloqueado = expedienteRepository.findBloqueadoByFolio(folio)
                .orElse(false);

        if (estaBloqueado) {
            return PlazoPrevencion.builder()
                    .folioExpediente(folio)
                    .fechaInicio(null)
                    .fechaLimite(null)
                    .diasHabilesRestantes(0)
                    .semaforoEstado("ROJO")
                    .vencido(true)
                    .build();
        }

        LocalDateTime fechaInicio = expedienteRepository
                .findFechaPrevencionByFolio(folio)
                .orElseThrow(() -> new InvalidRequestException(
                        "Expediente no encontrado o no está En Prevención: " + folio));

        LocalDate inicio         = fechaInicio.toLocalDate();
        LocalDate busquedaHasta  = inicio.plusDays(30);

        List<DiaInhabil> inhabiles = diaInhabilPort.findByRangoFechas(inicio, busquedaHasta);
        Set<LocalDate> fechasInhabiles = inhabiles.stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());

        LocalDate fechaLimite = sumarDiasHabiles(inicio, DIAS_HABILES_PLAZO, fechasInhabiles);

        LocalDate hoy = LocalDate.now();
        List<DiaInhabil> inhabilesRestantes = diaInhabilPort.findByRangoFechas(hoy, fechaLimite);
        Set<LocalDate> fechasInhabilesRestantes = inhabilesRestantes.stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());

        int diasRestantes = contarDiasHabiles(hoy, fechaLimite, fechasInhabilesRestantes);
        boolean vencido = !hoy.isBefore(fechaLimite);

         if (vencido) {
        try {
            cerrarExpedienteVencidoEnNuevaTransaccion(folio);
        } catch (Exception e) {
            log.error("[PlazoPrevencion] Error al cerrar expediente vencido {}: {}",
                    folio, e.getMessage(), e);
        }
        }

        String semaforo = calcularSemaforo(diasRestantes, vencido);

        return PlazoPrevencion.builder()
        .folioExpediente(folio)
        .fechaInicio(fechaInicio)
        .fechaLimite(fechaLimite.atStartOfDay())
        .diasHabilesRestantes(vencido ? 0 : diasRestantes)
        .semaforoEstado(semaforo)
        .vencido(vencido)
        .build();
    }

    public int calcularDiasHabilesRestantes(LocalDateTime fechaInicio, int diasHabiles) {
        if (fechaInicio == null || diasHabiles <= 0) {
            return 0;
        }

        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate busquedaHasta = inicio.plusDays(Math.max(diasHabiles * 3L, 30L));

        List<DiaInhabil> inhabiles = diaInhabilPort.findByRangoFechas(inicio, busquedaHasta);
        Set<LocalDate> fechasInhabiles = inhabiles.stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());

        LocalDate fechaLimite = sumarDiasHabiles(inicio, diasHabiles, fechasInhabiles);
        LocalDate hoy = LocalDate.now();

        if (!hoy.isBefore(fechaLimite)) {
            return 0;
        }

        List<DiaInhabil> inhabilesRestantes = diaInhabilPort.findByRangoFechas(hoy, fechaLimite);
        Set<LocalDate> fechasInhabilesRestantes = inhabilesRestantes.stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());

        return contarDiasHabiles(hoy, fechaLimite, fechasInhabilesRestantes);
    }

    private LocalDate sumarDiasHabiles(LocalDate desde, int dias, Set<LocalDate> inhabiles) {
        LocalDate fecha = desde;
        int contados = 0;
        while (contados < dias) {
            fecha = fecha.plusDays(1);
            if (esDiaHabil(fecha, inhabiles)) contados++;
        }
        return fecha;
    }

    private int contarDiasHabiles(LocalDate desde, LocalDate hasta, Set<LocalDate> inhabiles) {
        int count = 0;
        LocalDate fecha = desde.plusDays(1);
        while (!fecha.isAfter(hasta)) {
            if (esDiaHabil(fecha, inhabiles)) count++;
            fecha = fecha.plusDays(1);
        }
        return count;
    }

    private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> inhabiles) {
        return fecha.getDayOfWeek() != DayOfWeek.SATURDAY
                && fecha.getDayOfWeek() != DayOfWeek.SUNDAY
                && !inhabiles.contains(fecha);
    }

    private String calcularSemaforo(int diasRestantes, boolean vencido) {
        if (vencido)            return "ROJO";
        if (diasRestantes <= 1) return "AMARILLO";
        return "VERDE";
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
public void cerrarExpedienteVencidoEnNuevaTransaccion(String folio) {
    expedienteRepository.cerrarExpedienteVencido(folio);
    quejaJpaRepository.findIdExpedienteByFolio(folio)
        .ifPresent(idExpediente ->
            quejaJpaRepository.actualizarEstatusQueja(idExpediente, EstatusQuejaIds.CERRADA));
}
}
