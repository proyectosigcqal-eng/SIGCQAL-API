package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Mapper;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto.BandejaRepresentacionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BandejaRepresentacionMapper {

    public RepresentacionBandeja toDomain(Object[] row) {
        return RepresentacionBandeja.builder()
            .folio(asString(row[0]))
            .idExpediente(asString(row[1]))
            .municipioProcedencia(asString(row[2]))
            .contribuyente(asString(row[3]))
            .tipoActo(asString(row[4]))
            .estatusPrincipal(asString(row[5]))
            .estatusSecundario(asString(row[6]))
            .ultimaModificacionDescripcion(asString(row[7]))
            .ultimaModificacionTimestamp(asString(row[8]))
            .bloqueado(asBoolean(row[9]))
            .tieneFicha(asBoolean(row[10]))
            .tieneCir(asBoolean(row[11]))
            .tieneDemanda(asBoolean(row[12]))
            .tieneOficio(asBoolean(row[13]))
            .tieneAudiencia(asBoolean(row[14]))
            .tieneSentencia(asBoolean(row[15]))
            .tieneEjecutoria(asBoolean(row[16]))
            .tieneCumplimiento(asBoolean(row[17]))
            .fechaCir(asString(row[18]))
            .fechaDemanda(asString(row[19]))
            .fechaOficio(asString(row[20]))
            .fechaAudiencia(asString(row[21]))
            .fechaSentencia(asString(row[22]))
            .fechaEjecutoria(asString(row[23]))
            .build();
    }

    public BandejaRepresentacionResponseDto toDto(RepresentacionBandeja item) {
        UltimaModificacionDto ultimaModificacion = UltimaModificacionDto.builder()
            .descripcion(item.getUltimaModificacionDescripcion())
            .timestamp(item.getUltimaModificacionTimestamp())
            .build();

        return BandejaRepresentacionResponseDto.builder()
            .folio(item.getFolio())
            .idExpediente(item.getIdExpediente())
            .municipioProcedencia(item.getMunicipioProcedencia())
            .contribuyente(item.getContribuyente())
            .tipoActo(item.getTipoActo())
            .estatusPrincipal(item.getEstatusPrincipal())
            .estatusSecundario(item.getEstatusSecundario())
            .ultimaModificacion(ultimaModificacion)
            .bloqueado(item.getBloqueado())
            .tieneFicha(item.getTieneFicha())
            .tieneCir(item.getTieneCir())
            .tieneDemanda(item.getTieneDemanda())
            .tieneOficio(item.getTieneOficio())
            .tieneAudiencia(item.getTieneAudiencia())
            .tieneSentencia(item.getTieneSentencia())
            .tieneEjecutoria(item.getTieneEjecutoria())
            .tieneCumplimiento(item.getTieneCumplimiento())
            .fechaCir(item.getFechaCir())
            .fechaDemanda(item.getFechaDemanda())
            .fechaOficio(item.getFechaOficio())
            .fechaAudiencia(item.getFechaAudiencia())
            .fechaSentencia(item.getFechaSentencia())
            .fechaEjecutoria(item.getFechaEjecutoria())
            .build();
    }

    private String asString(Object value) {
        return value != null ? value.toString() : null;
    }

    private Boolean asBoolean(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean b) {
            return b;
        }
        return Boolean.parseBoolean(value.toString());
    }
}
