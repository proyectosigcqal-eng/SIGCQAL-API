package com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Entity.TipoCorrespondenciaEntity;

public interface TipoCorrespondenciaJpaRepository extends JpaRepository<TipoCorrespondenciaEntity, Integer> {
    Optional<TipoCorrespondenciaEntity> findByIdNatural(String idNatural);

    List<TipoCorrespondenciaEntity> findByActivoTrue();
}
