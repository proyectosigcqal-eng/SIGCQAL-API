package com.sigcqal.api.application.ModuloAreaSustantiva.InformeAutoridad;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.InformeAutoridad.Model.EstadoAlertaPlazo;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CalculadorDiasHabiles {

    private static final int RANGO_BUSQUEDA_DIAS = 60;
    private final DiaInhabilRepositoryPort diaInhabilRepositoryPort;

    public LocalDate calcularFechaLimite(LocalDateTime fechaInicio, int diasHabiles) {
        LocalDate inicio = fechaInicio.toLocalDate();
        Set<LocalDate> diasInhabiles = cargarInhabiles(inicio, inicio.plusDays(RANGO_BUSQUEDA_DIAS));

        LocalDate fecha = inicio;
        int contados = 0;
        while (contados < diasHabiles) {
            fecha = fecha.plusDays(1);
            if (esDiaHabil(fecha, diasInhabiles)) {
                contados++;
            }
        }
        return fecha;
    }

    public int diasHabilesRestantes(LocalDate fechaLimite) {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(fechaLimite)) {
            return 0;
        }

        Set<LocalDate> diasInhabiles = cargarInhabiles(hoy, fechaLimite);
        int restantes = 0;
        LocalDate cursor = hoy;
        while (!cursor.isAfter(fechaLimite)) {
            if (esDiaHabil(cursor, diasInhabiles)) {
                restantes++;
            }
            cursor = cursor.plusDays(1);
        }
        return Math.max(restantes, 0);
    }

    public EstadoAlertaPlazo determinarEstado(LocalDate fechaLimite) {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(fechaLimite)) {
            return EstadoAlertaPlazo.ROJO;
        }

        int diasRestantes = diasHabilesRestantes(fechaLimite);
        if (diasRestantes <= 1) {
            return EstadoAlertaPlazo.AMARILLO;
        }
        return EstadoAlertaPlazo.VERDE;
    }

    private Set<LocalDate> cargarInhabiles(LocalDate desde, LocalDate hasta) {
        return diaInhabilRepositoryPort.findByRangoFechas(desde, hasta)
                .stream()
                .map(dia -> dia.getFecha())
                .collect(Collectors.toSet());
    }

    private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> diasInhabiles) {
        return fecha.getDayOfWeek() != DayOfWeek.SATURDAY
                && fecha.getDayOfWeek() != DayOfWeek.SUNDAY
                && !diasInhabiles.contains(fecha);
    }
}

