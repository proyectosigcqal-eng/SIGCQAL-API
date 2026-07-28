package com.sigcqal.api.infra.Catalogo.Asesor.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;

public interface AsesorJpaRepository extends JpaRepository<AsesorEntity, Long> {
    List<AsesorEntity> findByActivoTrue(); 
    Optional<AsesorEntity> findByIdPersona(Long idPersona); 
    // Necesito saber el puente usuario→asesor para escribir esto exacto.
@Query("""
    SELECT a.idAsesor FROM AsesorEntity a
    INNER JOIN UsuarioEntity u ON u.idPersona = a.idPersona
    WHERE u.usuarioLogin = :username
    AND a.activo = true
    """)
Optional<Long> findIdAsesorByUsername(@Param("username") String username);
}