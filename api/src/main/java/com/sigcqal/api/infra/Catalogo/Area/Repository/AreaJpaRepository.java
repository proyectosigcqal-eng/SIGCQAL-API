package com.sigcqal.api.infra.Catalogo.Area.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;

public interface AreaJpaRepository extends JpaRepository<AreaEntity, Long> {}
