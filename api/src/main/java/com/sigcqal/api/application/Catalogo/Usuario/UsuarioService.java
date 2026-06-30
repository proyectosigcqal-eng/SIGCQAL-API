package com.sigcqal.api.application.Catalogo.Usuario;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.domain.Catalogo.Usuario.Port.UsuarioRepositoryPort;
import com.sigcqal.api.web.Admin.Dto.UsuarioAdminRequestDTO;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepositoryPort repositoryPort;
    private final PersonaRepositoryPort  personaRepositoryPort;
    private final AsesorRepositoryPort asesorRepositoryPort;
    private final BCryptPasswordEncoder  passwordEncoder;

 public List<UsuarioDTO> obtenerUsuarios() {
    return repositoryPort.findAll()
            .stream()
            .filter(u -> Boolean.TRUE.equals(u.getActivo())) // ✅ solo activos
            .map(this::mapToResponse)
            .toList();
}

    public UsuarioDTO obtenerUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Usuario usuario = repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        return mapToResponse(usuario);
    }

    private UsuarioDTO mapToResponse(Usuario dom) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(dom.getId());
        dto.setIdPersona(dom.getIdPersona());
        dto.setIdRoles(dom.getIdRoles());
        dto.setIdArea(dom.getIdArea());
        dto.setNombreArea(dom.getNombreArea());
        dto.setUsuarioLogin(dom.getUsuarioLogin());
        dto.setCorreoElectronico(dom.getCorreoElectronico());
        dto.setActivo(dom.getActivo()); 
        return dto;
    }

    public UsuarioDTO crearUsuario(UsuarioAdminRequestDTO request) {
    if (request.getNombre() == null || request.getUsuarioLogin() == null
            || request.getPassword() == null) {
        throw new InvalidRequestException("Nombre, usuario y contraseña son obligatorios.");
    }

    // 1. Crear persona
    Persona persona = new Persona();
    persona.setNombre(request.getNombre());
    persona.setApellidoPaterno(request.getApellidoPaterno());
    persona.setApellidoMaterno(request.getApellidoMaterno());
    persona.setCorreo(request.getCorreo());
    Long idPersona = personaRepositoryPort.save(persona).getId();

    // 2. Crear usuario
    Usuario usuario = new Usuario();
    usuario.setIdPersona(idPersona);
    usuario.setUsuarioLogin(request.getUsuarioLogin());
    usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    usuario.setCorreoElectronico(request.getCorreo());
    usuario.setIdArea(request.getIdArea());
    usuario.setActivo(true);

    return mapToResponse(repositoryPort.save(usuario));
}

public void actualizarRoles(Long idUsuario, List<Long> idRoles) {
    repositoryPort.actualizarRoles(idUsuario, idRoles);

    if (idRoles != null && idRoles.contains(2L)) {
        Usuario usuario = repositoryPort.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

         if (usuario.getIdPersona() == null) {
        System.out.println(">>> Usuario " + idUsuario + " sin idPersona, omitiendo creación de asesor");
    } else if (!asesorRepositoryPort.findByIdPersona(usuario.getIdPersona()).isPresent()) {
        Asesor asesor = new Asesor();
        asesor.setIdPersona(usuario.getIdPersona());
        asesor.setEspecialidad(null);
        asesor.setCargaActual(0);
        asesor.setActivo(true);
        asesorRepositoryPort.save(asesor);
    }

    
    }

    // ✅ Solo dar de baja si NO tiene rol asesor Y existe registro
    if (idRoles != null && !idRoles.contains(2L)) {
        Usuario usuario = repositoryPort.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getIdPersona() != null) {
            asesorRepositoryPort.findByIdPersona(usuario.getIdPersona())
                    .ifPresent(a -> asesorRepositoryPort.darBaja(a.getIdAsesor()));
        }
    }
}
public void darBajaUsuario(Long idUsuario) {
    repositoryPort.darBaja(idUsuario);
}

public List<UsuarioDTO> obtenerTodos() {
    return repositoryPort.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
}
}
