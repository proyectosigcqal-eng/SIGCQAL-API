package com.sigcqal.api.infra.Catalogo.Usuario.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByIdPersona(Long id);

}
