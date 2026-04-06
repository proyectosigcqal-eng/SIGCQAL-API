package com.sigcqal.api.infra.MAIQR_CU_28;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConvenioJpaRepository extends JpaRepository<ConvenioEntity, Long> {
    
    Optional<ConvenioEntity> findByNombreMunicipio(String nombreMunicipio);
}