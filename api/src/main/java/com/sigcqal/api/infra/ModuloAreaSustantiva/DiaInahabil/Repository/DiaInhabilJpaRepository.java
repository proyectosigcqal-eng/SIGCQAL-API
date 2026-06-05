package com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Entity.DiaInhabilEntity;

import java.time.LocalDate;
import java.util.List;

public interface DiaInhabilJpaRepository
        extends JpaRepository<DiaInhabilEntity, Integer> {

    @Query("SELECT d FROM DiaInhabilEntity d " +
           "WHERE d.activo = true " +
           "AND d.fecha >= :desde AND d.fecha <= :hasta")
    List<DiaInhabilEntity> findByRango(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);
}