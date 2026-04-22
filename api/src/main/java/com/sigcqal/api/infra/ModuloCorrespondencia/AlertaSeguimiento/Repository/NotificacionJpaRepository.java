package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.NotificacionEntity;

@Repository
public interface NotificacionJpaRepository extends JpaRepository<NotificacionEntity, Long> {}

