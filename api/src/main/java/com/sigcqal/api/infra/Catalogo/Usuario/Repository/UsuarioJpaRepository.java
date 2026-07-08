package com.sigcqal.api.infra.Catalogo.Usuario.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByIdPersona(Long id);

    // ✅ usuarioLogin — campo real en la entidad
    Optional<UsuarioEntity> findByUsuarioLogin(String usuarioLogin);

    // ✅ correoElectronico — campo real en la entidad
    Optional<UsuarioEntity> findByCorreoElectronico(String correoElectronico);
    boolean existsByCorreoElectronico(String correoElectronico);

    // ✅ usuarioLogin como "username" para el contexto de autenticación
    boolean existsByUsuarioLogin(String usuarioLogin);
}