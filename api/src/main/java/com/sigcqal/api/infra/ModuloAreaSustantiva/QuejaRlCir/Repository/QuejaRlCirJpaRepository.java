package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Entity.QuejaRlCirEntity;

@Repository
public interface QuejaRlCirJpaRepository extends JpaRepository<QuejaRlCirEntity, Long> {
    List<QuejaRlCirEntity> findByIdResolucionFinal(Long idResolucionFinal);
    
    @Query("SELECT q FROM QuejaRlCirEntity q WHERE q.idQuejaRlCir = :id")
    Optional<QuejaRlCirEntity> findByIdWithRelations(@Param("id") Long id);
}