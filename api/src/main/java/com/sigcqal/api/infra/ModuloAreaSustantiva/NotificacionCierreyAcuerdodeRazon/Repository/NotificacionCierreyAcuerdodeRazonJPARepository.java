package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity.NotificacionCierreyAcuerdodeRazonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionCierreyAcuerdodeRazonJPARepository extends JpaRepository<NotificacionCierreyAcuerdodeRazonEntity, Integer> {

    @Query("""
        SELECT n FROM NotificacionCierreyAcuerdodeRazonEntity n
        LEFT JOIN FETCH n.expediente
        LEFT JOIN FETCH n.usuarioCierre
        WHERE n.expediente.id = :idExpediente
        """)
    List<NotificacionCierreyAcuerdodeRazonEntity> findByExpediente_IdConRelaciones(
            @Param("idExpediente") Integer idExpediente);

    List<NotificacionCierreyAcuerdodeRazonEntity> findByExpediente_Id(Integer idExpediente);
}
