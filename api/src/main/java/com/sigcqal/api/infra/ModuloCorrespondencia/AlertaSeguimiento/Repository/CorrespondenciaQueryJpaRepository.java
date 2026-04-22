package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.CorrespondenciaRefEntity;

@Repository
public interface CorrespondenciaQueryJpaRepository extends JpaRepository<CorrespondenciaRefEntity, Long> {
    @Query("""
        SELECT new com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso(
            c.idFolio,
            c.folioUnico,
            c.numOficioExterno,
            c.dependenciaRemitente,
            c.nombreRemitente,
            a.nombre,
            a.idArea,
            e.nombreEstatus,
            c.fechaRegistro,
            0L
        )
        FROM CorrespondenciaRefEntity c
        LEFT JOIN CorrespondenciaTurnoRefEntity t
            ON t.idFolio = c.idFolio AND t.idTurno = (
                SELECT MAX(t2.idTurno)
                FROM CorrespondenciaTurnoRefEntity t2
                WHERE t2.idFolio = c.idFolio
            )
        LEFT JOIN t.areaDestino a
        LEFT JOIN c.estatus e
        WHERE e.nombreEstatus IN ('TURNADO', 'EN GESTIÓN')
          AND (:estatusFiltro IS NULL OR e.nombreEstatus = :estatusFiltro)
          AND (:idArea IS NULL OR a.idArea = :idArea)
          AND (:numFolio IS NULL OR c.folioUnico LIKE CONCAT('%', CONCAT(:numFolio, '%')))
          AND (:dependencia IS NULL OR c.dependenciaRemitente LIKE CONCAT('%', CONCAT(:dependencia, '%')))
          AND (:fechaInicio IS NULL OR c.fechaRegistro >= :fechaInicio)
          AND (:fechaFin IS NULL OR c.fechaRegistro <= :fechaFin)
      """)
    List<FolioConRetraso> findFoliosConRetraso(
            @Param("estatusFiltro") String estatusFiltro,
            @Param("idArea") Long idArea,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("numFolio") String numFolio,
            @Param("dependencia") String dependencia
    );

    @Query("""
        SELECT new com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso(
            c.idFolio,
            c.folioUnico,
            c.numOficioExterno,
            c.dependenciaRemitente,
            c.nombreRemitente,
            a.nombre,
            a.idArea,
            e.nombreEstatus,
            c.fechaRegistro,
            0L
        )
        FROM CorrespondenciaRefEntity c
        LEFT JOIN CorrespondenciaTurnoRefEntity t
            ON t.idFolio = c.idFolio AND t.idTurno = (
                SELECT MAX(t2.idTurno)
                FROM CorrespondenciaTurnoRefEntity t2
                WHERE t2.idFolio = c.idFolio
            )
        LEFT JOIN t.areaDestino a
        LEFT JOIN c.estatus e
        WHERE c.idFolio = :idFolio
      """)
    Optional<FolioConRetraso> findDetalleFolio(@Param("idFolio") Long idFolio);

    @Query("""
        SELECT e.nombreEstatus
        FROM CorrespondenciaRefEntity c
        JOIN c.estatus e
        WHERE c.idFolio = :idFolio
      """)
    Optional<String> obtenerEstatusActualFolio(@Param("idFolio") Long idFolio);
}

