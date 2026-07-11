package com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;

import java.util.List;
@Repository
public interface OficioJpaRepository extends JpaRepository<OficioEntity, Long> {
    Optional<OficioEntity> findByFolioUnico(String folioUnico);
    List<OficioEntity> findByAreaId(Long idArea);
    
    @Query("SELECT o FROM OficioEntity o LEFT JOIN FETCH o.area LEFT JOIN FETCH o.usuarioEmisor LEFT JOIN FETCH o.usuarioFirmante WHERE o.id = :id")
    Optional<OficioEntity> findByIdWithRelations(@Param("id") Long id);

    @Query("""
        SELECT o FROM OficioEntity o
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH o.usuarioEmisor
        LEFT JOIN FETCH o.usuarioFirmante
        """)
    List<OficioEntity> findAllConRelaciones();

    @Query("""
        SELECT o FROM OficioEntity o
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH o.usuarioEmisor
        LEFT JOIN FETCH o.usuarioFirmante
        WHERE o.area.id = :idArea
        """)
    List<OficioEntity> findByAreaIdConRelaciones(@Param("idArea") Long idArea);

    @Query("SELECT o FROM OficioEntity o " +
           "JOIN FETCH o.area " + // <--- Esto asegura que se traiga el nombre_area
           "WHERE NOT EXISTS (SELECT a FROM AcuseOficioEntity a WHERE a.oficio.id = o.id) " +
           "AND o.area.id = :idArea")
    List<OficioEntity> findOficiosSinAcusePorArea(@Param("idArea") Long idArea);

  @Query("""
    SELECT o FROM OficioEntity o
    LEFT JOIN FETCH o.area
    LEFT JOIN FETCH o.usuarioEmisor
    LEFT JOIN FETCH o.usuarioFirmante
    LEFT JOIN FETCH o.correspondencia
    WHERE o.area.id = :idArea
    AND EXISTS (
        SELECT a FROM AcuseOficioEntity a
        WHERE a.oficio.id = o.id
        AND a.esDelArea = true
    )
    AND NOT EXISTS (
    SELECT s FROM SeguimientoOficioEntity s
    WHERE s.oficio.id = o.id
    AND s.estatus.idEstatus IN (5, 6)
)
    """)
List<OficioEntity> findAsignadosActivosPorArea(@Param("idArea") Long idArea);

@Query("""
    SELECT o FROM OficioEntity o
    LEFT JOIN FETCH o.area
    LEFT JOIN FETCH o.usuarioEmisor
    LEFT JOIN FETCH o.usuarioFirmante
    LEFT JOIN FETCH o.correspondencia
    WHERE EXISTS (
        SELECT a FROM AcuseOficioEntity a
        WHERE a.oficio.id = o.id
        AND a.esDelArea = true
    )
    AND NOT EXISTS (
    SELECT s FROM SeguimientoOficioEntity s
    WHERE s.oficio.id = o.id
    AND s.estatus.idEstatus IN (5, 6)
)
    """)
List<OficioEntity> findTodosAsignadosActivos();

@Query("""
    SELECT o FROM OficioEntity o
    LEFT JOIN FETCH o.area
    WHERE NOT EXISTS (
        SELECT a FROM AcuseOficioEntity a
        WHERE a.oficio.id = o.id
    )
    """)
List<OficioEntity> findTodosSinAcuse();
}
