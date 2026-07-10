package com.sigcqal.api.infra.Catalogo.Usuario.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("""
        SELECT u FROM UsuarioEntity u
        LEFT JOIN FETCH u.idArea
        LEFT JOIN FETCH u.usuarioRoles ur
        LEFT JOIN FETCH ur.rol
        WHERE u.id = :id
        """)
    Optional<UsuarioEntity> findByIdConRelaciones(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT u FROM UsuarioEntity u
        LEFT JOIN FETCH u.idArea
        LEFT JOIN FETCH u.usuarioRoles ur
        LEFT JOIN FETCH ur.rol
        """)
    List<UsuarioEntity> findAllConRelaciones();

    @Query("""
        SELECT u FROM UsuarioEntity u
        LEFT JOIN FETCH u.idArea
        LEFT JOIN FETCH u.usuarioRoles ur
        LEFT JOIN FETCH ur.rol
        WHERE u.usuarioLogin = :login
        """)
    Optional<UsuarioEntity> findByUsuarioLoginConRelaciones(@Param("login") String usuarioLogin);

    Optional<UsuarioEntity> findByIdPersona(Long id);

    Optional<UsuarioEntity> findByUsuarioLogin(String usuarioLogin);

    Optional<UsuarioEntity> findByCorreoElectronico(String correoElectronico);

    boolean existsByCorreoElectronico(String correoElectronico);

    boolean existsByUsuarioLogin(String usuarioLogin);
}
