package com.sigcqal.api.infra.Catalogo.Expediente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.Catalogo.Expediente.Entity.ExpedienteEntity;
import java.util.List;

@Repository
public interface ExpedienteJpaRepository extends JpaRepository<ExpedienteEntity, Long> {
    ExpedienteEntity findByFolioGobierno(String folioGobierno);
}
