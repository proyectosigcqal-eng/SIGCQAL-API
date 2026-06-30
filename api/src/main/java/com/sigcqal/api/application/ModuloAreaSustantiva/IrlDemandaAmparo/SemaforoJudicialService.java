package com.sigcqal.api.application.ModuloAreaSustantiva.IrlDemandaAmparo;

import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.SemaforoJudicialDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemaforoJudicialService {

    private final DiaInhabilRepositoryPort diaInhabilPort;

    private static final int DIAS_HABILES_PLAZO = 15;

    /**
     * Calcula el semáforo judicial de 15 días hábiles.
     * Regla: el plazo inicia D+1 de la fecha base recibida (actualmente,
     * la fecha de generación de la demanda — ver IrlDemandaAmparoService.obtenerSemaforo()).
     * Descuenta sábados, domingos y días inhábiles de catalogos.dias_inhabiles.
     */
    public SemaforoJudicialDTO calcular(LocalDate fechaBase) {
        LocalDate inicio = fechaBase.plusDays(1);

        // Margen de búsqueda generoso: 15 días hábiles nunca superan 30 corridos
        // salvo períodos vacacionales largos — usamos 60 días como techo seguro
        LocalDate techo = inicio.plusDays(60);
        Set<LocalDate> inhabiles = cargarInhabiles(inicio, techo);

        LocalDate fechaLimite = calcularFechaLimite(inicio, inhabiles);
        LocalDate hoy = LocalDate.now();

        boolean vencido = hoy.isAfter(fechaLimite);
        int diasRestantes = vencido ? 0
                : contarDiasHabiles(hoy, fechaLimite, inhabiles);

        return SemaforoJudicialDTO.builder()
                .fechaPrimerPago(fechaBase) // nombre desactualizado: ahora es fecha de generación, no de pago
                .fechaInicioPlazo(inicio)
                .fechaLimite(fechaLimite)
                .diasHabilesRestantes(diasRestantes)
                .vencido(vencido)
                .color(determinarColor(diasRestantes, vencido))
                .mensaje(construirMensaje(diasRestantes, vencido, fechaLimite))
                .build();
    }

    // ── Privados ───────────────────────────────────────────────────────

    private Set<LocalDate> cargarInhabiles(LocalDate desde, LocalDate hasta) {
        return diaInhabilPort.findByRangoFechas(desde, hasta)
                .stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());
    }

    private LocalDate calcularFechaLimite(LocalDate inicio,
                                           Set<LocalDate> inhabiles) {
        LocalDate fecha = inicio;
        int contados = 0;
        while (contados < DIAS_HABILES_PLAZO) {
            if (esDiaHabil(fecha, inhabiles)) contados++;
            if (contados < DIAS_HABILES_PLAZO) fecha = fecha.plusDays(1);
        }
        return fecha;
    }

    private int contarDiasHabiles(LocalDate desde, LocalDate hasta,
                                   Set<LocalDate> inhabiles) {
        int count = 0;
        LocalDate fecha = desde;
        while (!fecha.isAfter(hasta)) {
            if (esDiaHabil(fecha, inhabiles)) count++;
            fecha = fecha.plusDays(1);
        }
        return count;
    }

    private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> inhabiles) {
        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) return false;
        if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY)   return false;
        return !inhabiles.contains(fecha);
    }

    // Verde ≥ 6 | Amarillo 3-5 | Rojo 1-2 | Gris = vencido
    private String determinarColor(int diasRestantes, boolean vencido) {
        if (vencido)            return "GRIS";
        if (diasRestantes >= 6) return "VERDE";
        if (diasRestantes >= 3) return "AMARILLO";
        return "ROJO";
    }

    private String construirMensaje(int dias, boolean vencido, LocalDate limite) {
        if (vencido) return "Plazo vencido el " + limite;
        return dias + " día(s) hábil(es) restante(s). Vence: " + limite;
    }
}