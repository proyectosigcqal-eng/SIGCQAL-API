package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Entity.NotificacionSentenciaCumplidaEntity;

public interface NotificacionSentenciaCumplidaJpaRepository
        extends JpaRepository<NotificacionSentenciaCumplidaEntity, Integer> {
}
