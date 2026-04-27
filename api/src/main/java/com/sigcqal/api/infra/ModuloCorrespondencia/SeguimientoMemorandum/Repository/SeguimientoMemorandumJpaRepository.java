package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Entity.SeguimientoMemorandumEntity;

public interface SeguimientoMemorandumJpaRepository extends JpaRepository<SeguimientoMemorandumEntity, Long> {

    List<SeguimientoMemorandumEntity> findByMemorandum_Id(Long idMemo);
}
