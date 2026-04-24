package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Entity.SeguimientoCorrespondenciaEntity;


public interface SeguimientoCorrepondenciaJpaRepository

extends JpaRepository<SeguimientoCorrespondenciaEntity,Long>{

   List<SeguimientoCorrespondenciaEntity> findByCorrespondencia_IdFolio(Integer idFolio);

   boolean exisexistsByCorrespondencia_IdFolio(Integer idFolio);

   List<SeguimientoCorrespondenciaEntity> findByUsuario_IdUsuario (Integer idUsuario);

    List<SeguimientoCorrespondenciaEntity> findByEstatus_IdEstatus (Integer idEstatus);

}