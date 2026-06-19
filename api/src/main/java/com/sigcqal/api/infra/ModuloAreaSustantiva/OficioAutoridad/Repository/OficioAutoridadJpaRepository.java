package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioAutoridad.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioAutoridad.Entity.OficioAutoridadEntity;
import java.util.List;

@Repository
public interface OficioAutoridadJpaRepository extends JpaRepository<OficioAutoridadEntity, Integer> {
    // Busca los oficios asociados a través del ARI
    List<OficioAutoridadEntity> findByAri_IdAri(Integer idAri);
}