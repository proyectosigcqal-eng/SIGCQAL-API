package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;

@Repository
public interface ResolucionFinalJPARepository extends JpaRepository<ResolucionFinalEntity, Integer> {

    List<ResolucionFinalEntity> findByIdExpediente(Integer idExpediente);

    boolean existsByIdExpediente(Integer idExpediente);
}