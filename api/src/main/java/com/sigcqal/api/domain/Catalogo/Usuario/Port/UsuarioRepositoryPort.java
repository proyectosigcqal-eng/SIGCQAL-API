package com.sigcqal.api.domain.Catalogo.Usuario.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
     Usuario save(Usuario usuario);
    void actualizarRoles(Long idUsuario, List<Long> idRoles);
    void darBaja(Long idUsuario);
    Optional<Usuario> findByUsuarioLogin(String login);
}
