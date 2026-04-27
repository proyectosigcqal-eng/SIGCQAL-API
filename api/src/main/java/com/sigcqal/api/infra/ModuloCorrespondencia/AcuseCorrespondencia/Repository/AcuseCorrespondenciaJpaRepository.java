package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Entity.AcuseCorrespondenciaEntity;

public interface AcuseCorrespondenciaJpaRepository
        extends JpaRepository<AcuseCorrespondenciaEntity, Long> {

    List<AcuseCorrespondenciaEntity> 
        findByEsDelAreaTrueAndCorrespondencia_Area_Id(Long idArea);
}