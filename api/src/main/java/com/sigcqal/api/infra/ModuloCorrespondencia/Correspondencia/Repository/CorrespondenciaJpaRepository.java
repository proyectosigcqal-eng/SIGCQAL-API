package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;

@Repository
public interface CorrespondenciaJpaRepository extends JpaRepository<CorrespondenciaEntity, Long> {
    boolean existsByNumeroOficio(String numeroOficio);

    Optional<CorrespondenciaEntity> findTopByFolioUnicoEndingWithOrderByIdDesc(String suffix);

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
}
