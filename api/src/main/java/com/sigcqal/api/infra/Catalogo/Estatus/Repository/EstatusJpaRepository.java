package com.sigcqal.api.infra.Catalogo.Estatus.Repository;
import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstatusJpaRepository extends JpaRepository<EstatusEntity, Integer> {

    Optional<EstatusEntity> findByNombreEstatus(String nombre);
}
