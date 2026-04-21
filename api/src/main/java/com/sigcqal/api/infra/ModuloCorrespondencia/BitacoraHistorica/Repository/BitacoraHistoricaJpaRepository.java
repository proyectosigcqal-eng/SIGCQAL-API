package com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Entity.BitacoraHistoricaEntity;

public interface BitacoraHistoricaJpaRepository extends JpaRepository<BitacoraHistoricaEntity, Long> {
   
    List<BitacoraHistoricaEntity> findByCorrespondenciaIdOrderByFechaMovimientoAsc(Long idCorrespondencia);

}