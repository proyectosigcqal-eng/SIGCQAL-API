package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteJPARepository extends JpaRepository<ExpedienteEntity, Integer> {

    Optional<ExpedienteEntity> findByFolioGobierno(String folioGobierno);
    Optional<ExpedienteEntity> findTopByFolioGobiernoStartingWithOrderByFolioGobiernoDesc(String prefix);
    java.util.List<ExpedienteEntity> findAllByFechaEnvioOficioIsNotNullAndFechaRecepcionInformeIsNull();
}
