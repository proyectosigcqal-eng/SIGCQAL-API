package com.sigcqal.api.application.Auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UsuarioRolRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioJpaRepository userRepository;
    private final UsuarioRolRepository userRolRepository;


     @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity user = userRepository.findByUsuarioLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
List<UsuarioRolEntity> roles = userRolRepository.findByUsuario_Id(user.getId());
        return new org.springframework.security.core.userdetails.User(
            user.getUsuarioLogin(),
            user.getPassword(),
            user.getActivo() != null && user.getActivo(),
            true, true, true,
            roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRol().getNombre()))
                .toList()
        );
    }
}
