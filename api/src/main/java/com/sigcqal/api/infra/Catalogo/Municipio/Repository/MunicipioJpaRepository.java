package com.sigcqal.api.infra.Catalogo.Municipio.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;

@Repository
public interface MunicipioJpaRepository extends JpaRepository<MunicipioEntity, Long> {
    Optional<MunicipioEntity> findByNombreMunicipio(String nombreMunicipio);
    
}
