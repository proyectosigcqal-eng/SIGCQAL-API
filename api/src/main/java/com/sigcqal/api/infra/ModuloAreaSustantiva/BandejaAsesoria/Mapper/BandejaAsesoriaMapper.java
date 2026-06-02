package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.BandejaAsesoriaResponseDto;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;

@Component
public class BandejaAsesoriaMapper {

    public TramiteBandeja toDomain(Object[] row) {
        if (row == null) return null;

        return TramiteBandeja.builder()
            .folio(row[0] != null ? row[0].toString() : null)
            .municipioProcedencia(row[1] != null ? row[1].toString() : null)
            .contribuyente(row[2] != null ? row[2].toString() : null)
            .tipoActo(row[3] != null ? row[3].toString() : null)
            .estatusPrincipal(row[4] != null ? row[4].toString() : null)
            .estatusSecundario(row[5] != null ? row[5].toString() : null)
            .ultimaModificacionDescripcion(row[6] != null ? row[6].toString() : null)
            .ultimaModificacionTimestamp(row[7] != null ? row[7].toString() : null)
            .tieneBitacora(row[8] != null ? Boolean.parseBoolean(row[8].toString()) : false)
            .tieneFicha(row[9] != null ? Boolean.parseBoolean(row[9].toString()) : false)
            .build();
    }

    public BandejaAsesoriaResponseDto toDto(TramiteBandeja tramite) {
    UltimaModificacionDto ultimaModificacion = UltimaModificacionDto.builder()
        .descripcion(tramite.getUltimaModificacionDescripcion())
        .timestamp(tramite.getUltimaModificacionTimestamp())
        .build();

    return BandejaAsesoriaResponseDto.builder()
        .folio(tramite.getFolio())
        .municipioProcedencia(tramite.getMunicipioProcedencia())
        .contribuyente(tramite.getContribuyente())
        .tipoActo(tramite.getTipoActo())
        .estatusPrincipal(tramite.getEstatusPrincipal())
        .estatusSecundario(tramite.getEstatusSecundario())
        .ultimaModificacion(ultimaModificacion)
        .tieneBitacora(tramite.getTieneBitacora())
        .tieneFicha(tramite.getTieneFicha())
        .build();
}

}
