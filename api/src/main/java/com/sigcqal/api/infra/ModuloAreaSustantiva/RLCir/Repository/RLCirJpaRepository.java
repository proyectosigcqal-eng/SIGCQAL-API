package com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Entity.RLCirEntity;

@Repository
public interface RLCirJpaRepository extends JpaRepository<RLCirEntity, Long> {
    List<RLCirEntity> findByIdExpediente(Long idExpediente);
    
    @Query("SELECT r FROM RLCirEntity r WHERE r.idRlCir = :id")
    Optional<RLCirEntity> findByIdWithRelations(@Param("id") Long id);
}