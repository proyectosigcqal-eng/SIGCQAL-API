package com.sigcqal.api.application.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UsuarioRolRepository;
import com.sigcqal.api.web.Auth.Dto.LoginRequestDTO;
import com.sigcqal.api.web.Auth.Dto.LoginResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioJpaRepository  usuarioRepo;
    private final UsuarioRolRepository  usuarioRolRepo;
    // ✅ rolRepo eliminado — ya no se necesita, UsuarioRolEntity tiene el RolEntity directo
    private final JwtService            jwtService;
    private final BCryptPasswordEncoder encoder;

    @Transactional // ✅ mantiene sesión JPA abierta para que LAZY funcione en el stream
    public LoginResponseDTO login(LoginRequestDTO request) {

        UsuarioEntity usuario = usuarioRepo
                .findByUsuarioLogin(request.getUsuarioLogin())
                .orElseThrow(() -> new InvalidRequestException("Credenciales incorrectas."));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new InvalidRequestException(
                    "La cuenta está desactivada. Contacta al administrador.");
        }

        if (!encoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new InvalidRequestException("Credenciales incorrectas.");
        }

        Integer idUsuario  = usuario.getId().intValue();
        Integer idArea     = usuario.getIdArea() != null
                ? usuario.getIdArea().getId().intValue()
                : null;
        String  nombreArea = usuario.getIdArea() != null
                ? usuario.getIdArea().getNombre()
                : null;

        // ✅ ur.getRol() directo — no getIdRol(), no rolRepo, no query extra
       List<Map<String, Object>> roles = usuarioRolRepo
        .findByUsuario_Id(usuario.getId())
        .stream()
        .<RolEntity>map(ur -> ur.getRol())   
        .filter(rol -> rol != null && Boolean.TRUE.equals(rol.getActivo()))
        .map(rol -> Map.<String, Object>of(
                "idRol",     rol.getId(),       
                "nombreRol", rol.getNombre(),     
                "urlBase",   ""                  
        ))
        .collect(Collectors.toList());
               

        String token = jwtService.generarToken(
                idUsuario,
                usuario.getUsuarioLogin(),
                idArea,
                roles);

        return LoginResponseDTO.builder()
                .token(token)
                .idUsuario(idUsuario)
                .usuarioLogin(usuario.getUsuarioLogin())
                .idArea(idArea)
                .nombreArea(nombreArea)
                .roles(roles)
                .build();
    }
}