package com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Entity.EstatusDetalleExpedienteEntity;

public interface EstatusDetalleExpedienteJpaRepository extends JpaRepository<EstatusDetalleExpedienteEntity, Long> {}
