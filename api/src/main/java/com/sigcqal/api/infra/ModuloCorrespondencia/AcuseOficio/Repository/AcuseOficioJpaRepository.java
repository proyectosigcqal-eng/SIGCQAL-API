package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Entity.AcuseOficioEntity;

public interface AcuseOficioJpaRepository extends JpaRepository<AcuseOficioEntity, Long> {

    List<AcuseOficioEntity> findByEsDelAreaTrueAndOficio_Area_Id(Long idArea);

    boolean existsByOficio_Id(Long idOficio);
}