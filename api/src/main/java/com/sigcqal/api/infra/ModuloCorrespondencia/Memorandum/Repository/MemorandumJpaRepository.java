package com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;

import java.util.List;
@Repository
public interface MemorandumJpaRepository extends JpaRepository<MemorandumEntity, Long> {
    Optional<MemorandumEntity> findByFolioUnico(String folioUnico);
    List<MemorandumEntity> findByAreaId(Long idArea);
    
    @Query("""
    SELECT m FROM MemorandumEntity m 
    LEFT JOIN FETCH m.correspondencia c
    LEFT JOIN FETCH m.area 
    LEFT JOIN FETCH m.usuarioEmisor 
    LEFT JOIN FETCH m.usuarioFirmante 
    WHERE m.id = :id
    """)
Optional<MemorandumEntity> findByIdWithRelations(@Param("id") Long id);

    @Query("""
            SELECT m 
            FROM MemorandumEntity m
            WHERE m.area.id = :idArea
            AND NOT EXISTS (
                SELECT 1 
                FROM AcuseReciboInternoEntity a
                WHERE a.memorandum.id = m.id
            )
        """)
        List<MemorandumEntity> findByAreaSinAcuse(@Param("idArea") Long idArea);

        // Memorándums CON acuse del área PERO sin seguimiento = "Asignados en curso"
@Query("""
    SELECT m FROM MemorandumEntity m
    LEFT JOIN FETCH m.area
    LEFT JOIN FETCH m.usuarioEmisor
    LEFT JOIN FETCH m.usuarioFirmante
    LEFT JOIN FETCH m.correspondencia
    WHERE m.area.id = :idArea
    AND EXISTS (
        SELECT a FROM AcuseReciboInternoEntity a
        WHERE a.memorandum.id = m.id
        AND a.esDelArea = true
    )
    AND NOT EXISTS (
    SELECT s FROM SeguimientoMemorandumEntity s
    WHERE s.memorandum.id = m.id
    AND s.estatus.idEstatus IN (5, 6)
)
    """)
List<MemorandumEntity> findAsignadosActivosPorArea(@Param("idArea") Long idArea);

// Admin ve todos los asignados activos (todas las áreas)
@Query("""
    SELECT m FROM MemorandumEntity m
    LEFT JOIN FETCH m.area
    LEFT JOIN FETCH m.usuarioEmisor
    LEFT JOIN FETCH m.usuarioFirmante
    LEFT JOIN FETCH m.correspondencia
    WHERE EXISTS (
        SELECT a FROM AcuseReciboInternoEntity a
        WHERE a.memorandum.id = m.id
        AND a.esDelArea = true
    )
    AND NOT EXISTS (
    SELECT s FROM SeguimientoMemorandumEntity s
    WHERE s.memorandum.id = m.id
    AND s.estatus.idEstatus IN (5, 6)
)
    """)
List<MemorandumEntity> findTodosAsignadosActivos();

@Query("""
    SELECT m FROM MemorandumEntity m
    LEFT JOIN FETCH m.area
    WHERE NOT EXISTS (
        SELECT a FROM AcuseReciboInternoEntity a
        WHERE a.memorandum.id = m.id
    )
    """)
List<MemorandumEntity> findTodosSinAcuse();
}