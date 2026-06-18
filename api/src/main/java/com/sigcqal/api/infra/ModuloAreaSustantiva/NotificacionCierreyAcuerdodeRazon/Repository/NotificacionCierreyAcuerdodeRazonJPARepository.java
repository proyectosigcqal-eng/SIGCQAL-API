package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity.NotificacionCierreyAcuerdodeRazonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionCierreyAcuerdodeRazonJPARepository extends JpaRepository<NotificacionCierreyAcuerdodeRazonEntity, Integer> {

    List<NotificacionCierreyAcuerdodeRazonEntity> findByExpediente_Id (Integer idExpediente);
    
}
