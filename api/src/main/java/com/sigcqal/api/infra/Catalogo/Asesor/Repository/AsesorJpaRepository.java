package com.sigcqal.api.infra.Catalogo.Asesor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;

public interface AsesorJpaRepository extends JpaRepository<AsesorEntity, Long> {}
