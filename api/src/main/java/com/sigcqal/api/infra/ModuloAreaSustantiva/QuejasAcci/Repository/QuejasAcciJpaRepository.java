package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity.QuejasAcciEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuejasAcciJpaRepository extends JpaRepository<QuejasAcciEntity, Long> {
    List<QuejasAcciEntity> findByIdQueja(Long idQueja);
}