package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Entity.SeguimientoMemorandumEntity;

@Repository
public interface SeguimientoMemorandumJpaRepository extends JpaRepository<SeguimientoMemorandumEntity, Long> {

    // Para listarTodos()
    @Query("""
        SELECT s FROM SeguimientoMemorandumEntity s
        LEFT JOIN FETCH s.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        """)
    List<SeguimientoMemorandumEntity> findAllConRelaciones();

    // Para listarPorMemorandumId()
    @Query("""
        SELECT s FROM SeguimientoMemorandumEntity s
        LEFT JOIN FETCH s.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE m.id = :idMemo
        """)
    List<SeguimientoMemorandumEntity> findByMemorandum_IdConRelaciones(@Param("idMemo") Long idMemo);

    // Para buscarPorId() y para el fix del LazyInitializationException en actualizar()
    @Query("""
        SELECT s FROM SeguimientoMemorandumEntity s
        LEFT JOIN FETCH s.memorandum m
        LEFT JOIN FETCH m.correspondencia
        LEFT JOIN FETCH m.area
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE s.id = :id
        """)
    Optional<SeguimientoMemorandumEntity> findByIdConRelaciones(@Param("id") Long id);
}