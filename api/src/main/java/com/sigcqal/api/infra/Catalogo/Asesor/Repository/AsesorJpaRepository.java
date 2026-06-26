package com.sigcqal.api.infra.Catalogo.Asesor.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;

public interface AsesorJpaRepository extends JpaRepository<AsesorEntity, Long> {
    List<AsesorEntity> findByActivoTrue(); 
    Optional<AsesorEntity> findByIdPersona(Long idPersona); 
}
