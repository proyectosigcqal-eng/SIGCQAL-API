package com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;

import java.util.List;
@Repository
public interface MemorandumJpaRepository extends JpaRepository<MemorandumEntity, Long> {
    Optional<MemorandumEntity> findByFolioUnico(String folioUnico);

    List<MemorandumEntity> findByAreaId(Long idArea);
}