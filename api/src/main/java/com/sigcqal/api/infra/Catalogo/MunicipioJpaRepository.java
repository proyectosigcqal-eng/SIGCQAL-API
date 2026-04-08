package com.sigcqal.api.infra.Catalogo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioJpaRepository extends JpaRepository<MunicipioEntity, Long> {
    Optional<MunicipioEntity> findByNombreMunicipio(String nombreMunicipio);
    
}
