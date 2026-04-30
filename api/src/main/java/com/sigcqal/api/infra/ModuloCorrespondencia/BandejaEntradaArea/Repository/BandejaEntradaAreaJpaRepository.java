package com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Entity.BandejaEntradaAreaEntity;

@Repository
public interface BandejaEntradaAreaJpaRepository extends JpaRepository<BandejaEntradaAreaEntity, Long> {
    List<BandejaEntradaAreaEntity> findByArea_Id(Long idArea);

    List<BandejaEntradaAreaEntity> findByCorrespondencia_Id(Long idCorrespondencia);
}

