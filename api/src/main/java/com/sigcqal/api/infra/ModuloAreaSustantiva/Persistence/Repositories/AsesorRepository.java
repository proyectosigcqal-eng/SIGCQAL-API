package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities.AsesorEntity;// Asegúrate de importar tus Entidades

@Repository
public interface AsesorRepository extends JpaRepository<AsesorEntity, Long> {
    // Aquí no tienes que escribir nada más por ahora.
    // Spring te da automáticamente: save, findById, findAll, delete, etc.
}