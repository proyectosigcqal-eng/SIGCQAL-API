package com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;

public interface EstatusExpedienteJpaRepository extends JpaRepository<EstatusExpedienteEntity, Long> {
    Optional<EstatusExpedienteEntity> findByNombreIgnoreCase(String nombre);
}
