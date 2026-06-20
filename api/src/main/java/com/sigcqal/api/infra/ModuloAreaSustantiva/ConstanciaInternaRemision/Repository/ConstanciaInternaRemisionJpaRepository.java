package com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity.ConstanciaInternaRemisionEntity;

public interface ConstanciaInternaRemisionJpaRepository 
    extends JpaRepository<ConstanciaInternaRemisionEntity, Long> {

    /**
     * Busca la CIR más reciente por id_queja
     * QuejaEntity.idQueja es Integer, convertir a Long para la búsqueda
     */
    @Query("SELECT c FROM ConstanciaInternaRemisionEntity c " +
           "WHERE CAST(c.queja.idQueja AS long) = :quejaId " +
           "ORDER BY c.fechaCreacion DESC LIMIT 1")
    Optional<ConstanciaInternaRemisionEntity> findByQuejaId(@Param("quejaId") Long quejaId);

    /**
     * Verifica si existe una CIR para una queja específica
     */
    @Query("SELECT COUNT(c) > 0 FROM ConstanciaInternaRemisionEntity c " +
           "WHERE CAST(c.queja.idQueja AS long) = :quejaId")
    boolean existeByQuejaId(@Param("quejaId") Long quejaId);

    /**
     * Busca la CIR más reciente por id_expediente
     * Permite búsqueda tanto directa como a través de la relación queja → expediente
     */
    @Query("SELECT c FROM ConstanciaInternaRemisionEntity c " +
           "WHERE c.expediente.id = :expedienteId " +
           "ORDER BY c.fechaCreacion DESC LIMIT 1")
    Optional<ConstanciaInternaRemisionEntity> findLatestByExpedienteId(@Param("expedienteId") Integer expedienteId);

    /**
     * Alternativa: buscar a través de queja si no hay relación directa
     */
    @Query("SELECT c FROM ConstanciaInternaRemisionEntity c " +
           "WHERE c.queja.expediente.id = :expedienteId " +
           "ORDER BY c.fechaCreacion DESC LIMIT 1")
    Optional<ConstanciaInternaRemisionEntity> findLatestByExpedienteIdViaQueja(@Param("expedienteId") Integer expedienteId);
}