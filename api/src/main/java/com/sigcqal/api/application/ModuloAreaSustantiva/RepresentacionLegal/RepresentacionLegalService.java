package com.sigcqal.api.application.ModuloAreaSustantiva.RepresentacionLegal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.infra.ModuloAreaSustantiva.RepresentacionLegal.Repository.RepresentacionLegalJpaRepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.RepresentacionLegal.Dto.BandejaIrlResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepresentacionLegalService {

    private final RepresentacionLegalJpaRepository repository;

    public List<BandejaIrlResponseDTO> obtenerBandeja(Boolean esEvolucion, String search, Integer idEstatus) {
        String searchParam = (search != null && !search.isBlank()) ? search.trim() : null;
        List<Object[]> rows = repository.findBandeja(esEvolucion, searchParam, idEstatus);
        return rows.stream().map(this::mapearFila).toList();
    }

    /**
     * Índices: 0 id, 1 folio_gobierno, 2 contribuyente, 3 asesor,
     * 4 municipio, 5 estatus, 6 fecha_creacion,
     * 7 es_evolucion, 8 id_estatus
     */
    private BandejaIrlResponseDTO mapearFila(Object[] row) {
        BandejaIrlResponseDTO dto = new BandejaIrlResponseDTO();
        dto.setId(toInteger(row[0]));
        dto.setFolioGobierno(toString(row[1]));
        dto.setContribuyente(toString(row[2]));
        dto.setAsesor(toString(row[3]));
        dto.setMunicipio(toString(row[4]));
        dto.setEstatus(toString(row[5]));
        dto.setFechaCreacion(toLocalDateTime(row[6]));
        dto.setEsEvolucion(toBoolean(row[7]));
        dto.setIdEstatus(toInteger(row[8]));
        return dto;
    }

    private Integer toInteger(Object val) {
        if (val == null)
            return null;
        if (val instanceof Number n)
            return n.intValue();
        return null;
    }

    private String toString(Object val) {
        if (val == null)
            return "";
        String s = val.toString().trim();
        return s.equals("null") ? "" : s;
    }

    private Boolean toBoolean(Object val) {
        if (val == null)
            return false;
        if (val instanceof Boolean b)
            return b;
        return Boolean.parseBoolean(val.toString());
    }

    private LocalDateTime toLocalDateTime(Object val) {
        if (val == null)
            return null;
        if (val instanceof Timestamp ts)
            return ts.toLocalDateTime();
        if (val instanceof java.sql.Date d)
            return d.toLocalDate().atStartOfDay();
        return null;
    }
}