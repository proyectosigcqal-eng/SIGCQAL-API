package com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity.ContestacionAutoridadEntity;


public interface ContestacionAutoridadJpaRepository 
    extends JpaRepository<ContestacionAutoridadEntity, Long> {
    Optional<ContestacionAutoridadEntity> findByFolioExpediente(String folio);


}