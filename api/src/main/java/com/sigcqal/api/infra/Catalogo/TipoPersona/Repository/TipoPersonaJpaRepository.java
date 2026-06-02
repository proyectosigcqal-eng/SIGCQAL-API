package com.sigcqal.api.infra.Catalogo.TipoPersona.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.TipoPersona.Entity.TipoPersonaEntity;

public interface TipoPersonaJpaRepository extends JpaRepository<TipoPersonaEntity, Long> {
}
