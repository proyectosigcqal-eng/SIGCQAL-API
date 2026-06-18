package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;

@Repository
public interface QuejasAriJpaRepository extends JpaRepository<QuejasAriEntity, Long> {
    Optional<QuejasAriEntity> findByNumExpedienteOficial(String numExpediente);
    List<QuejasAriEntity> findByIdQueja(Long idQueja);
    
    @Query("SELECT q FROM QuejasAriEntity q WHERE q.idAri = :id")
    Optional<QuejasAriEntity> findByIdWithRelations(@Param("id") Long id);
}
