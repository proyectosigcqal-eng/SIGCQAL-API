package com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Entity.ClasificacionJuridicaEntity;

public interface ClasifiacionJuridicaJPARepository extends JpaRepository<ClasificacionJuridicaEntity, Integer> {
    List<ClasificacionJuridicaEntity> findAllByIdExpediente(Integer idExpediente);

}
