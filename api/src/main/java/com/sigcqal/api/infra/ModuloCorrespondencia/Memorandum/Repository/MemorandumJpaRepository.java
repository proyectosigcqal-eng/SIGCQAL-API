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
}