package com.sigcqal.api.infra.Catalogo.Empleado.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Empleado.Entity.EmpleadoEntity;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoEntity, Long> {}
