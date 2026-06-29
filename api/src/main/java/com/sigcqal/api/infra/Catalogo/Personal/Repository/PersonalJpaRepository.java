package com.sigcqal.api.infra.Catalogo.Personal.Repository;

import com.sigcqal.api.infra.Catalogo.Personal.Entity.PersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Spring Data JPA para la entidad Personal.
 * Proporciona métodos CRUD básicos automáticamente.
 */
@Repository
public interface PersonalJpaRepository extends JpaRepository<PersonalEntity, Long> {
    
    // Si en el futuro necesitas consultas personalizadas, 
    // como buscar por ID de persona, puedes añadirlas aquí:
    // Optional<PersonalEntity> findByPersona_IdPersona(Long idPersona);
}
