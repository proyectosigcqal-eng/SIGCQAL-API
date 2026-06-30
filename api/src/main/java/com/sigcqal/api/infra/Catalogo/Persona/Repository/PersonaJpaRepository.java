package com.sigcqal.api.infra.Catalogo.Persona.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

public interface PersonaJpaRepository extends JpaRepository<PersonaEntity, Long> {
    Optional<PersonaEntity> findByRfc(String rfc);

    @Query("""
        SELECT p FROM PersonaEntity p
        WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
           OR LOWER(p.apellidoPaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
           OR LOWER(p.apellidoMaterno) LIKE LOWER(CONCAT('%', :texto, '%'))
           OR LOWER(p.rfc) LIKE LOWER(CONCAT('%', :texto, '%'))
        ORDER BY p.nombre
        """)
    List<PersonaEntity> buscarPorNombreORfc(@Param("texto") String texto);
}