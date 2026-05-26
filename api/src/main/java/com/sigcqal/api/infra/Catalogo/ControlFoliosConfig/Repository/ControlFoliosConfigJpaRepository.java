package com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Entity.ControlFoliosConfigEntity;

public interface ControlFoliosConfigJpaRepository extends JpaRepository<ControlFoliosConfigEntity, Long> {}
