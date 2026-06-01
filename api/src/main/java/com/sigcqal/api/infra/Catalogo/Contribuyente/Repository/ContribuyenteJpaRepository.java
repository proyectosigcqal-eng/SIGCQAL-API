package com.sigcqal.api.infra.Catalogo.Contribuyente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.Catalogo.Contribuyente.Entity.ContribuyenteEntity;
import java.util.Optional;

@Repository
public interface ContribuyenteJpaRepository extends JpaRepository<ContribuyenteEntity, Long> {
    Optional<ContribuyenteEntity> findByIdPersona(Long idPersona);
}
