package com.sigcqal.api.infra.Catalogo.Persona.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

public interface PersonaJpaRepository extends JpaRepository<PersonaEntity, Long> {}
