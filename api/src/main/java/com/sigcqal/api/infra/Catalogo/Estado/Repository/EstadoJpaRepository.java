package com.sigcqal.api.infra.Catalogo.Estado.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.Estado.Entity.EstadoEntity;

@Repository
public interface EstadoJpaRepository extends JpaRepository<EstadoEntity, Long> {
    Optional<EstadoEntity> findByNombreEstado(String nombreEstado);
    
}
