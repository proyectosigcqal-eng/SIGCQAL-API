package com.sigcqal.api.infra.Catalogo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoJpaRepository extends JpaRepository<EstadoEntity, Long> {
    Optional<EstadoEntity> findByNombreEstado(String nombreEstado);
    
}
