package com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity.ConstanciaInternaRemisionEntity;

public interface ConstanciaInternaRemisionJpaRepository 
    extends JpaRepository<ConstanciaInternaRemisionEntity, Long> {
}
