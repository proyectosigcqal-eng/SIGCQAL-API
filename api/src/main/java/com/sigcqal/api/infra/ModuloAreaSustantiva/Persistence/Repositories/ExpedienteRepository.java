package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities.ExpedienteEntity; // Asegúrate de importar tus Entidades

@Repository
public interface ExpedienteRepository extends JpaRepository<ExpedienteEntity, Long> {
    // Aquí no tienes que escribir nada más por ahora.
    // Spring te da automáticamente: save, findById, findAll, delete, etc.
}