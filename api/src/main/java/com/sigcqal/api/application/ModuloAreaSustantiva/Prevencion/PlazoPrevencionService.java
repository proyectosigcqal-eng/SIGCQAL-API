// package com.sigcqal.api.application.ModuloAreaSustantiva.Prevencion;

// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import com.sigcqal.api.application.exception.InvalidRequestException;
// import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
// import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
// import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.PlazoPrevencion.Model.PlazoPrevencion;

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// @Service
// @RequiredArgsConstructor
// public class PlazoPrevencionService {

//     private static final int DIAS_HABILES_PLAZO = 3;

//     private final DiaInhabilRepositoryPort diaInhabilPort;
//     private final ExpedientePrevencionRepository expedienteRepository;

//     public PlazoPrevencion calcularPlazo(String folio) {

//         // Obtener fecha de inicio (cuando cambió a "En Prevención")
//         LocalDateTime fechaInicio = expedienteRepository
//                 .findFechaPrevencionByFolio(folio)
//                 .orElseThrow(() -> new InvalidRequestException(
//                         "Expediente no encontrado o no está En Prevención: " + folio));

//         LocalDate inicio = fechaInicio.toLocalDate();
//         LocalDate busquedaHasta = inicio.plusDays(30); // rango seguro

//         // Traer días inhábiles del rango
//         List<DiaInhabil> inhabiles = diaInhabilPort.findByRangoFechas(inicio, busquedaHasta);
//         Set<LocalDate> fechasInhabiles = inhabiles.stream()
//                 .map(DiaInhabil::getFecha)
//                 .collect(Collectors.toSet());

//         // Calcular fecha límite sumando exactamente 3 días hábiles
//         LocalDate fechaLimite = sumarDiasHabiles(inicio, DIAS_HABILES_PLAZO, fechasInhabiles);

//         // Calcular días hábiles restantes desde hoy
//         LocalDate hoy = LocalDate.now();
//         List<DiaInhabil> inhabilesRestantes = diaInhabilPort.findByRangoFechas(hoy, fechaLimite);
//         Set<LocalDate> fechasInhabilesRestantes = inhabilesRestantes.stream()
//                 .map(DiaInhabil::getFecha)
//                 .collect(Collectors.toSet());

//         int diasRestantes = contarDiasHabiles(hoy, fechaLimite, fechasInhabilesRestantes);
//         boolean vencido = hoy.isAfter(fechaLimite);

//         String semaforo = calcularSemaforo(diasRestantes, vencido);

//         return PlazoPrevencion.builder()
//                 .folioExpediente(folio)
//                 .fechaInicio(fechaInicio)
//                 .fechaLimite(fechaLimite)
//                 .diasHabilesRestantes(vencido ? 0 : diasRestantes)
//                 .semaforoEstado(semaforo)
//                 .vencido(vencido)
//                 .build();
//     }

//     // ── Lógica de cálculo de días hábiles ──────────────────────────────

//     private LocalDate sumarDiasHabiles(LocalDate desde, int dias,
//                                         Set<LocalDate> inhabiles) {
//         LocalDate fecha = desde;
//         int contados = 0;
//         while (contados < dias) {
//             fecha = fecha.plusDays(1);
//             if (esDiaHabil(fecha, inhabiles)) contados++;
//         }
//         return fecha;
//     }

//     private int contarDiasHabiles(LocalDate desde, LocalDate hasta,
//                                    Set<LocalDate> inhabiles) {
//         int count = 0;
//         LocalDate fecha = desde;
//         while (!fecha.isAfter(hasta)) {
//             if (esDiaHabil(fecha, inhabiles)) count++;
//             fecha = fecha.plusDays(1);
//         }
//         return count;
//     }

//     private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> inhabiles) {
//         return fecha.getDayOfWeek() != DayOfWeek.SATURDAY
//                 && fecha.getDayOfWeek() != DayOfWeek.SUNDAY
//                 && !inhabiles.contains(fecha);
//     }

//     private String calcularSemaforo(int diasRestantes, boolean vencido) {
//         if (vencido)            return "ROJO";
//         if (diasRestantes <= 1) return "AMARILLO";
//         return "VERDE";
//     }
// }