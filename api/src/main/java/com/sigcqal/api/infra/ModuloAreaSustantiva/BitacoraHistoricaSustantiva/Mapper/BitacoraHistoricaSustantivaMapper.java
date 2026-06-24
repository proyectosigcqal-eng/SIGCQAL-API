package com.sigcqal.api.infra.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Mapper;

import org.springframework.stereotype.Component;

// Importa tus entidades aquí
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity.QuejasAcciEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity.ConstanciaInternaRemisionEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity.ContestacionAutoridadEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Dto.BitacoraHistoricaSustantivaResponseDto;
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity.NotificacionCierreyAcuerdodeRazonEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioAutoridad.Entity.OficioAutoridadEntity;

@Component
public class BitacoraHistoricaSustantivaMapper {

    // Método para ACCI
    public BitacoraHistoricaSustantiva mapAcciToDomain(QuejasAcciEntity entity) {
        return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Acción de Investigación (ACCI)")
            .fecha(entity.getFechaEmisionAcci()) // Asegura convertir LocalDate a LocalDateTime
            .autorCompleto("Sistema")
            .descripcion("ACCI: " + entity.getJustificacionInvestigacion())
            .estatus(Boolean.TRUE.equals(entity.getConcluido()) ? "Concluido" : "En Proceso")
            .fuente("ACCI")
            .rutaArchivo(entity.getRutaPdfAcci()) // Asumiendo que la entidad tiene un campo para la ruta del PDF
            .build();
    }

    // Cambia esto en BitacoraHistoricaMapper
    public BitacoraHistoricaSustantiva mapRespuestaToDomain(ContestacionAutoridadEntity entity) {
        return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Respuesta de Autoridad")
            .fecha(entity.getFechaRegistro()) // Ajustado a la fecha de registro de la respuesta
            .autorCompleto(entity.getNombreTitular())
            .estatus("Recibida")
            .fuente("Respuesta de Autoridad")
            .rutaArchivo(entity.getRutaPdfInforme())
            .build();
    }
// ... otros imports
    public BitacoraHistoricaSustantiva mapQuejaToDomain(QuejaEntity q) {
    // Usamos getFolioGobierno() en lugar de getFolio()
    String folio = (q.getExpediente() != null) ? q.getExpediente().getFolioGobierno() : "Sin Folio";

    return BitacoraHistoricaSustantiva.builder()
        .tipoEvento("Registro de Queja")
        .fecha(q.getFechaRegistro())
        .descripcion("Folio Gobierno: " + folio) 
        .estatus("Registrada")
        .fuente("Queja")
        .build();
}

    public BitacoraHistoricaSustantiva mapAriToDomain(QuejasAriEntity ari) {
        return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Acuerdo de Inicio")
            .fecha(ari.getFechaAcuerdo())
            .descripcion("ARI generado")
            .rutaArchivo(ari.getRutaPdfAri())
            .build();
    }

    // En BitacoraHistoricaMapper.java
    public BitacoraHistoricaSustantiva mapResolucionToDomain(ResolucionFinalEntity entity) {
        return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Resolución Final")
            .fecha(entity.getFechaEmisionResolucion().atStartOfDay())
            .descripcion("Concepto: " + entity.getConceptoCobro() + ". Folio Crédito: " + entity.getFolioCredito())
            .estatus("Finalizado")
            .fuente("ResolucionFinal")
            .build();
    }

    // Método para Oficio de Autoridad
    public BitacoraHistoricaSustantiva mapOficioToDomain(OficioAutoridadEntity entity) {
            // Si la entidad es nula, regresamos null (o maneja el error según tu arquitectura)
            if (entity == null) return null;

            return BitacoraHistoricaSustantiva.builder()
                .tipoEvento("Oficio de Autoridad")
                // Convertimos LocalDate a LocalDateTime al inicio del día para mantener consistencia
                .fecha(entity.getFechaEnvioOficio() != null ? entity.getFechaEnvioOficio().atStartOfDay() : null)
                .autorCompleto("Comisionado")
                .descripcion("Oficio: " + entity.getNumOficioComisionado())
                .estatus("Enviado")
                .fuente("OficioAutoridad")
                .build();
        }

        // Método para Notificación de Cierre y Acuerdo de Razón
    // En BitacoraHistoricaMapper.java
    public BitacoraHistoricaSustantiva mapNotificacionToDomain(NotificacionCierreyAcuerdodeRazonEntity entity, String nombrePersona) {
        if (entity == null) return null;

        return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Cierre de Expediente")
            .fecha(entity.getFechaCierre())
            .autorCompleto(nombrePersona) // Usamos el nombre que ya resolvimos en el adapter
            .descripcion("Medio de notificación: " + entity.getMedioNotificacion())
            .estatus("Cerrado")
            .fuente("NotificacionCierre")
            .rutaArchivo(entity.getRutaArchivoAcuerdo())
            .build();
    }

    // En BitacoraHistoricaMapper.java
    public BitacoraHistoricaSustantivaResponseDto mapToDto(BitacoraHistoricaSustantiva domain) {
        if (domain == null) return null;

        return BitacoraHistoricaSustantivaResponseDto.builder()
                .tipoEvento(domain.getTipoEvento())
                .fecha(domain.getFecha())
                .autorCompleto(domain.getAutorCompleto())
                .descripcion(domain.getDescripcion())
                .estatus(domain.getEstatus())
                .fuente(domain.getFuente())
                .rutaArchivo(domain.getRutaArchivo())
                .build();
    }

    public BitacoraHistoricaSustantiva mapCirToDomain(ConstanciaInternaRemisionEntity cir) {
    return BitacoraHistoricaSustantiva.builder()
            .tipoEvento("Constancia Interna de Remisión")
            .fecha(cir.getFechaCreacion()) // Usamos el campo que vimos en tu Entity
            .descripcion("Observaciones: " + cir.getObservaciones())
            .estatus("Generado") // O el estatus que consideres apropiado
            .fuente("CIR")
            .rutaArchivo(cir.getRutaPdfCir())
            .build();
    }

}