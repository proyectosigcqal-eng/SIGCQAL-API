package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity.QuejasAcciEntity;

@Repository
public interface QuejasAcciJpaRepository extends JpaRepository<QuejasAcciEntity, Integer> {
    // Es mejor usar el nombre del objeto relacionado en el método
    List<QuejasAcciEntity> findByQueja_IdQueja(Integer idQueja);
}