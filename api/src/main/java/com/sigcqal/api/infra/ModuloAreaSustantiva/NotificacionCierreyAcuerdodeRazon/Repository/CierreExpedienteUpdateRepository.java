package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Usamos cualquier entidad existente solo para que JPA no se queje
import com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity.NotificacionCierreyAcuerdodeRazonEntity;

@Repository
public interface CierreExpedienteUpdateRepository extends JpaRepository<NotificacionCierreyAcuerdodeRazonEntity, Integer> {

    @Modifying
    @Query(value = "UPDATE sustantiva.expedientes SET bloqueado = true, id_estatus_expediente = 5 WHERE id_expediente = :id", nativeQuery = true)
    void actualizarEstatusExpediente(@Param("id") Integer id);
}