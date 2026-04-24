package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Entity.SeguimientoMemorandumEntity;

public interface SeguimientoMemorandumJpaRepository 

    extends JpaRepository<SeguimientoMemorandumEntity, Long>{

    
            List<SeguimientoMemorandumEntity> findByMemorandum_IdMemo(Integer idMemo);

            
            boolean existsByMemorandum_IdMemo(Integer idMemo);

           
            List<SeguimientoMemorandumEntity> findByUsuario_IdUsuario(Integer idUsuario);

           
            List<SeguimientoMemorandumEntity> findByEstatus_IdEstatus(Integer idEstatus);
            }