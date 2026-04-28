package com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionCorrespondencia.Adapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port.IReasignacionCorrespondenciaPort;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReasignacionCorrespondenciaAdapter implements IReasignacionCorrespondenciaPort {

    private final JdbcTemplate jdbcTemplate;

    private static final String BASE_SELECT = """
            SELECT
                ac.id_acuse_correspondencia,
                ac.id_correspondencia,
                ac.id_usuario_revisor,
                ac.fecha_aceptacion,
                ac.hora_aceptacion,
                ac.es_del_area,
                ac.estatus_rechazado,
                c.folio_unico,
                c.num_oficio_externo,
                c.dependencia_remitente,
                c.nombre_remitente,
                c.asunto,
                c.fecha_oficio,
                c.fecha_recibido,
                c.observaciones_validacion,
                c.id_area
            FROM acuse_correspondencia ac
            LEFT JOIN correspondencia c ON c.id_correspondencia = ac.id_correspondencia
            """;

    @Override
    public Optional<ReasignacionCorrespondenciaResponseDTO> listarPorId(Long id) {
        try {
            String sql = BASE_SELECT + " WHERE ac.id_acuse_correspondencia = ?";
            List<ReasignacionCorrespondenciaResponseDTO> resultados = jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> mapRow(rs),
                    id
            );

            return resultados.stream().findFirst();
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<ReasignacionCorrespondenciaResponseDTO> listarPorArea(Long idArea) {
        try {
            String sql = BASE_SELECT + " WHERE c.id_area = ?";
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> mapRow(rs),
                    idArea
            );
        } catch (DataAccessException ex) {
            return List.of();
        }
    }

    @Override
    public List<ReasignacionCorrespondenciaResponseDTO> listarPorEstatusRechazado(Boolean estatusRechazado) {
        try {
            String sql = BASE_SELECT + " WHERE ac.estatus_rechazado = ?";
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> mapRow(rs),
                    estatusRechazado
            );
        } catch (DataAccessException ex) {
            return List.of();
        }
    }

    private ReasignacionCorrespondenciaResponseDTO mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        ReasignacionCorrespondenciaResponseDTO dto = new ReasignacionCorrespondenciaResponseDTO();

        dto.setIdAcuseCorrespondencia(rs.getObject("id_acuse_correspondencia", Long.class));
        dto.setIdCorrespondencia(rs.getObject("id_correspondencia", Long.class));
        dto.setIdUsuarioRevisor(rs.getObject("id_usuario_revisor", Long.class));
        dto.setEsDelArea(rs.getObject("es_del_area", Boolean.class));
        dto.setEstatusRechazado(rs.getObject("estatus_rechazado", Boolean.class));

        LocalDate fechaAceptacion = rs.getObject("fecha_aceptacion", LocalDate.class);
        LocalTime horaAceptacion = rs.getObject("hora_aceptacion", LocalTime.class);
        LocalDate fechaOficio = rs.getObject("fecha_oficio", LocalDate.class);
        LocalDate fechaRecibido = rs.getObject("fecha_recibido", LocalDate.class);

        dto.setFechaAceptacion(fechaAceptacion != null ? fechaAceptacion.toString() : null);
        dto.setHoraAceptacion(horaAceptacion != null ? horaAceptacion.toString() : null);
        dto.setFechaExpedicion(fechaOficio != null ? fechaOficio.toString() : null);
        dto.setFechaRecibido(fechaRecibido != null ? fechaRecibido.toString() : null);

        dto.setFolioUnico(rs.getString("folio_unico"));
        dto.setNumeroOficio(rs.getString("num_oficio_externo"));
        dto.setDependenciaRemitente(rs.getString("dependencia_remitente"));
        dto.setTitularDependencia(rs.getString("nombre_remitente"));
        dto.setAsunto(rs.getString("asunto"));
        dto.setObservaciones(rs.getString("observaciones_validacion"));
        dto.setIdArea(rs.getObject("id_area", Long.class));

        return dto;
    }
}
