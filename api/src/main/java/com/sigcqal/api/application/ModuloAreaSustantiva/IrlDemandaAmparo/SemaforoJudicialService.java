package com.sigcqal.api.application.ModuloAreaSustantiva.IrlDemandaAmparo;

import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model.IrlDemandaAmparo;
import com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Port.IrlDemandaAmparoRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto.SemaforoJudicialDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SemaforoJudicialService {

    private static final int DIAS_HABILES_PLAZO = 15;

    private final IrlDemandaAmparoRepositoryPort port;
    private final DiaInhabilRepositoryPort       diaInhabilPort;

    public SemaforoJudicialDTO calcularSemaforoJudicial(Integer idDemandaAmparo) {

        IrlDemandaAmparo demanda = port.findById(idDemandaAmparo)
                .orElseThrow(() -> new RuntimeException(
                        "Demanda de amparo no encontrada: " + idDemandaAmparo));

        LocalDateTime fechaGeneracion = demanda.getFechaGeneracionDemanda();

        if (fechaGeneracion == null) {
            return SemaforoJudicialDTO.builder()
                    .diasHabilesRestantes(DIAS_HABILES_PLAZO)
                    .fechaLimite(null)
                    .color("GRIS")
                    .vencido(false)
                    .mensaje("El semáforo iniciará cuando se genere la demanda.")
                    .build();
        }

        LocalDate inicio = fechaGeneracion.toLocalDate();
        LocalDate hasta  = inicio.plusDays(60);

        List<DiaInhabil> inhabiles = diaInhabilPort.findByRangoFechas(inicio, hasta);
        Set<LocalDate> fechasInhabiles = inhabiles.stream()
                .map(DiaInhabil::getFecha)
                .collect(Collectors.toSet());

        LocalDate fechaLimite   = sumarDiasHabiles(inicio, DIAS_HABILES_PLAZO, fechasInhabiles);
        LocalDate hoy           = LocalDate.now();
        boolean   vencido       = !hoy.isBefore(fechaLimite);

        // ✅ Reutiliza el mismo set de inhabiles ya cargado,
        // evitando que una query diferente devuelva resultados distintos
        int diasRestantes = vencido ? 0 : contarDiasHabiles(hoy, fechaLimite, fechasInhabiles);

        String estado;
        String mensaje;

        if (vencido) {
            estado  = "ROJO";
            mensaje = "El plazo para interponer la demanda ha vencido.";
        } else if (diasRestantes <= 3) {
            estado  = "ROJO";
            mensaje = diasRestantes == 1
                    ? "¡Queda 1 día hábil! Presenta la demanda hoy."
                    : "¡Quedan " + diasRestantes + " días hábiles! Urgente.";
        } else if (diasRestantes <= 7) {
            estado  = "AMARILLO";
            mensaje = "Quedan " + diasRestantes + " días hábiles para presentar la demanda.";
        } else {
            estado  = "VERDE";
            mensaje = "Quedan " + diasRestantes + " días hábiles para presentar la demanda.";
        }

        return SemaforoJudicialDTO.builder()
                .diasHabilesRestantes(diasRestantes)
                .fechaLimite(fechaLimite)
                .color(estado)
                .vencido(vencido)
                .mensaje(mensaje)
                .build();
    }

    private LocalDate sumarDiasHabiles(LocalDate desde, int dias,
                                       Set<LocalDate> inhabiles) {
        LocalDate fecha    = desde;
        int       contados = 0;
        while (contados < dias) {
            fecha = fecha.plusDays(1);
            if (esDiaHabil(fecha, inhabiles)) contados++;
        }
        return fecha;
    }

    private int contarDiasHabiles(LocalDate desde, LocalDate hasta,
                                  Set<LocalDate> inhabiles) {
        int count = 0;
        LocalDate fecha = desde.plusDays(1); // ✅ excluye hoy, solo cuenta días futuros
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
}