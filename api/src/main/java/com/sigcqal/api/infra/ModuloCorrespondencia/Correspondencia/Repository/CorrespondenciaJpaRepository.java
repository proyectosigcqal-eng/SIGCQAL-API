package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;

@Repository
public interface CorrespondenciaJpaRepository extends JpaRepository<CorrespondenciaEntity, Long> {
    boolean existsByNumeroOficio(String numeroOficio);

    Optional<CorrespondenciaEntity> findTopByFolioUnicoEndingWithOrderByIdDesc(String suffix);

    @Query("""
        SELECT c FROM CorrespondenciaEntity c
        LEFT JOIN FETCH c.estatus
        LEFT JOIN FETCH c.usuarioCaptura
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH c.tipoCorrespondencia
        WHERE c.id = :id
        """)
    Optional<CorrespondenciaEntity> findByIdConRelaciones(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT c FROM CorrespondenciaEntity c
        LEFT JOIN FETCH c.estatus
        LEFT JOIN FETCH c.usuarioCaptura
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH c.tipoCorrespondencia
        """)
    List<CorrespondenciaEntity> findAllConRelaciones();

    @Query("""
        SELECT c FROM CorrespondenciaEntity c
        LEFT JOIN FETCH c.estatus
        LEFT JOIN FETCH c.usuarioCaptura
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH c.tipoCorrespondencia
        WHERE c.area.id = :idArea
        """)
    List<CorrespondenciaEntity> findByArea_IdConRelaciones(@Param("idArea") Long idArea);

    @Query("""
        SELECT c FROM CorrespondenciaEntity c
        LEFT JOIN FETCH c.estatus
        LEFT JOIN FETCH c.usuarioCaptura
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH c.tipoCorrespondencia
        WHERE c.area.id = :idArea
        AND NOT EXISTS (
            SELECT 1
            FROM AcuseCorrespondenciaEntity a
            WHERE a.correspondencia.id = c.id
        )
        """)
    List<CorrespondenciaEntity> findByArea_IdAndWithoutAcuseConRelaciones(@Param("idArea") Long idArea);

    @Query("""
        SELECT c FROM CorrespondenciaEntity c
        LEFT JOIN FETCH c.estatus
        LEFT JOIN FETCH c.usuarioCaptura
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH c.tipoCorrespondencia
        JOIN c.tipoCorrespondencia t
        WHERE UPPER(t.descripcion) = UPPER(:descripcion)
        ORDER BY c.fechaRecibido DESC, c.id DESC
        """)
    List<CorrespondenciaEntity> findByTipoDescripcionConRelaciones(@Param("descripcion") String descripcion);

    List<CorrespondenciaEntity> findByArea_Id(Long idArea);

    @Query("""
    SELECT c
    FROM CorrespondenciaEntity c
    WHERE c.area.id = :idArea
    AND NOT EXISTS (
        SELECT 1
        FROM AcuseCorrespondenciaEntity a
        WHERE a.correspondencia.id = c.id
    )
""")
List<CorrespondenciaEntity> findByArea_IdAndWithoutAcuse(@Param("idArea") Long idArea);

    @Query("""
        SELECT c FROM CorrespondenciaEntity c
        JOIN c.tipoCorrespondencia t
        WHERE UPPER(t.descripcion) = UPPER(:descripcion)
        ORDER BY c.fechaRecibido DESC, c.id DESC
    """)
    List<CorrespondenciaEntity> findByTipoDescripcion(@Param("descripcion") String descripcion);

    @Query("""
    SELECT c FROM CorrespondenciaEntity c
    LEFT JOIN FETCH c.estatus
    LEFT JOIN FETCH c.usuarioCaptura
    LEFT JOIN FETCH c.area
    LEFT JOIN FETCH c.tipoCorrespondencia
    WHERE c.area IS NULL
    """)
List<CorrespondenciaEntity> findSinAreaAsignada();

}

