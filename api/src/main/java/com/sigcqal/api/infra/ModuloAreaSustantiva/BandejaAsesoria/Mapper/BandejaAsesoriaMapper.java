package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.BandejaAsesoriaResponseDto;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;

@Component
public class BandejaAsesoriaMapper {

   public TramiteBandeja toDomain(Object[] row) {
    return TramiteBandeja.builder()
        .folio(row[0] != null ? row[0].toString() : null)
        .idExpediente(row[1] != null ? row[1].toString() : null)  // ← nuevo
        .municipioProcedencia(row[2] != null ? row[2].toString() : null)
        .contribuyente(row[3] != null ? row[3].toString() : null)
        .tipoActo(row[4] != null ? row[4].toString() : null)
        .estatusPrincipal(row[5] != null ? row[5].toString() : null)
        .estatusSecundario(row[6] != null ? row[6].toString() : null)
        .ultimaModificacionDescripcion(row[7] != null ? row[7].toString() : null)
        .ultimaModificacionTimestamp(row[8] != null ? row[8].toString() : null)
        .bloqueado(row[9] != null ? (Boolean) row[9] : false)
        .tieneFicha(row[10] != null ? (Boolean) row[10] : false)
        .tieneCir(row[11] != null ? (Boolean) row[11] : false)
        .tieneAri(row[12] != null ? (Boolean) row[12] : false)
        .tieneOficio(row[13] != null ? (Boolean) row[13] : false)
        .tieneContestacion(row[14] != null ? (Boolean) row[14] : false)
        .tieneAcci(row[15] != null ? (Boolean) row[15] : false)
        .tieneResolucion(row[16] != null ? (Boolean) row[16] : false)
        .checklistCompleto(row[17] != null ? (Boolean) row[17] : false)
        .fechaCir(row[18] != null ? row[18].toString() : null)
        .fechaAri(row[19] != null ? row[19].toString() : null)
        .fechaOficio(row[20] != null ? row[20].toString() : null)
        .fechaContestacion(row[21] != null ? row[21].toString() : null)
        .fechaAcci(row[22] != null ? row[22].toString() : null)
        .fechaResolucion(row[23] != null ? row[23].toString() : null)
        .build();
}
    public BandejaAsesoriaResponseDto toDto(TramiteBandeja tramite) {
    UltimaModificacionDto ultimaModificacion = UltimaModificacionDto.builder()
        .descripcion(tramite.getUltimaModificacionDescripcion())
        .timestamp(tramite.getUltimaModificacionTimestamp())
        .build();

    return BandejaAsesoriaResponseDto.builder()
        .folio(tramite.getFolio())
        .idExpediente(tramite.getIdExpediente())
        .municipioProcedencia(tramite.getMunicipioProcedencia())
        .contribuyente(tramite.getContribuyente())
        .tipoActo(tramite.getTipoActo())
        .estatusPrincipal(tramite.getEstatusPrincipal())
        .estatusSecundario(tramite.getEstatusSecundario())
        .ultimaModificacion(ultimaModificacion)
        .tieneBitacora(tramite.getTieneBitacora())
        .tieneFicha(tramite.getTieneFicha())
        .tieneCir(tramite.getTieneCir())
        .tieneAri(tramite.getTieneAri())
        .tieneOficio(tramite.getTieneOficio())
        .tieneContestacion(tramite.getTieneContestacion())
        .tieneAcci(tramite.getTieneAcci())
        .tieneResolucion(tramite.getTieneResolucion())
        .bloqueado(tramite.getBloqueado())
        .checklistCompleto(tramite.getChecklistCompleto())
        .fechaCir(tramite.getFechaCir())
        .fechaAri(tramite.getFechaAri())
        .fechaOficio(tramite.getFechaOficio())
        .fechaContestacion(tramite.getFechaContestacion())
        .fechaAcci(tramite.getFechaAcci())
        .fechaResolucion(tramite.getFechaResolucion())
        .build();
    }

}
