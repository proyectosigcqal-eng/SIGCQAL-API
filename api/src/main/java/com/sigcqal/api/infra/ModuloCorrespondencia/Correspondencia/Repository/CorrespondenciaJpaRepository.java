package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;

@Repository
public interface CorrespondenciaJpaRepository extends JpaRepository<CorrespondenciaEntity, Long> {
    boolean existsByNumeroOficio(String numeroOficio);

    Optional<CorrespondenciaEntity> findTopByFolioUnicoEndingWithOrderByIdDesc(String suffix);
}
