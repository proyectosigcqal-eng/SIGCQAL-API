package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteJPARepository extends JpaRepository<ExpedienteEntity, Integer> {

    @Query("""
        SELECT e FROM ExpedienteEntity e
        LEFT JOIN FETCH e.contribuyente c
        LEFT JOIN FETCH c.persona
        LEFT JOIN FETCH e.municipio
        LEFT JOIN FETCH e.asesor
        LEFT JOIN FETCH e.tipoTramite
        LEFT JOIN FETCH e.estatusExpediente
        WHERE e.folioGobierno = :folio
        """)
    Optional<ExpedienteEntity> findByFolioGobiernoConRelaciones(@Param("folio") String folioGobierno);

    @Query("""
        SELECT e FROM ExpedienteEntity e
        LEFT JOIN FETCH e.contribuyente c
        LEFT JOIN FETCH c.persona
        LEFT JOIN FETCH e.municipio
        LEFT JOIN FETCH e.asesor
        LEFT JOIN FETCH e.tipoTramite
        LEFT JOIN FETCH e.estatusExpediente
        WHERE e.id = :id
        """)
    Optional<ExpedienteEntity> findByIdConRelaciones(@Param("id") Integer id);

    Optional<ExpedienteEntity> findByFolioGobierno(String folioGobierno);
    boolean existsByFolioGobierno(String folioGobierno);

    Optional<ExpedienteEntity> findTopByFolioGobiernoStartingWithOrderByFolioGobiernoDesc(String prefix);

    List<ExpedienteEntity> findByFechaEnvioOficioAutoridadIsNotNullAndFechaRecepcionInformeIsNull();
     @Modifying
        @Query(value = "UPDATE sustantiva.expedientes SET bloqueado = true WHERE id_expediente = :idExpediente", nativeQuery = true)
        void marcarBloqueado(@Param("idExpediente") Integer idExpediente);
}
