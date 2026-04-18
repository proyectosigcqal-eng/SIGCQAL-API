package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity.AcuseReciboInternoEntity;

public interface AcuseReciboInternoJpaRepository 
    extends JpaRepository<AcuseReciboInternoEntity, Long> {

    List<AcuseReciboInternoEntity> findByUsuarioRevisor_Id(Long idUsuario);

        boolean existsByMemorandum_Id(Long idMemorandum);
}