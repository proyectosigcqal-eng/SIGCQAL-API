package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

public interface QuejaJPARepository extends JpaRepository<QuejaEntity, Integer> {
}