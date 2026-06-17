package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Entity.OficioNotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OficioNotificacionJpaRepository
        extends JpaRepository<OficioNotificacionEntity, Long> {


    List<OficioNotificacionEntity> findByFolioExpedienteOrderByFechaGeneracionDesc(
            String folioExpediente);
}