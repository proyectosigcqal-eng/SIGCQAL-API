package com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Entity.DetalleAsesoriaEntity;
import java.util.Optional;

@Repository
public interface DetalleAsesoriaJpaRepository extends JpaRepository<DetalleAsesoriaEntity, Long> {
    
    Optional<DetalleAsesoriaEntity> findByIdExpediente(Long idExpediente);
    Optional<DetalleAsesoriaEntity> findFirstByIdExpedienteOrderByFechaNotificacionDesc(Long idExpediente);
    
}