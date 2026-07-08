package com.sigcqal.api.application.Catalogo.Usuario;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.domain.Catalogo.Asesor.Port.AsesorRepositoryPort;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.domain.Catalogo.Usuario.Port.UsuarioRepositoryPort;
import com.sigcqal.api.web.Admin.Dto.UsuarioAdminRequestDTO;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.ActualizarUsuarioRequestDTO;
import com.sigcqal.api.web.Catalogo.Usuario.Dto.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // ✅ adaptado del externo — optimiza sesiones Hibernate en lecturas
public class UsuarioService {

    private final UsuarioRepositoryPort  repositoryPort;
    private final PersonaRepositoryPort  personaRepositoryPort;
    private final AsesorRepositoryPort   asesorRepositoryPort;
    private final BCryptPasswordEncoder  passwordEncoder;

    // ── CONSULTAS (heredan readOnly = true) ──────────────────────────────────

    public List<UsuarioDTO> obtenerUsuarios() {
        return repositoryPort.findAll()
                .stream()
                .filter(u -> Boolean.TRUE.equals(u.getActivo()))
                .map(this::mapToResponse)
                .toList();
    }

    public List<UsuarioDTO> obtenerTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UsuarioDTO obtenerUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }
        return mapToResponse(
            repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id))
        );
    }

    // ✅ adaptado del externo — equivalente a findByUsername, necesario para AuthService
    public UsuarioDTO obtenerPorLogin(String usuarioLogin) {
        return repositoryPort.findByUsuarioLogin(usuarioLogin)
                .map(this::mapToResponse)
                .orElseThrow(() -> new InvalidRequestException(
                        "Usuario no encontrado: " + usuarioLogin));
    }

    // ── ESCRITURA (sobrescriben readOnly con @Transactional propio) ──────────

    @Transactional // ✅ adaptado del externo
    public UsuarioDTO crearUsuario(UsuarioAdminRequestDTO request) {
        if (request.getNombre() == null || request.getUsuarioLogin() == null
                || request.getPassword() == null) {
            throw new InvalidRequestException("Nombre, usuario y contraseña son obligatorios.");
        }

        Persona persona = new Persona();
        persona.setNombre(request.getNombre());
        persona.setApellidoPaterno(request.getApellidoPaterno());
        persona.setApellidoMaterno(request.getApellidoMaterno());
        persona.setCorreo(request.getCorreo());
        Long idPersona = personaRepositoryPort.save(persona).getId();

        Usuario usuario = new Usuario();
        usuario.setIdPersona(idPersona);
        usuario.setUsuarioLogin(request.getUsuarioLogin());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setCorreoElectronico(request.getCorreo());
        usuario.setIdArea(request.getIdArea());
        usuario.setActivo(true);

        return mapToResponse(repositoryPort.save(usuario));
    }

    @Transactional // ✅ adaptado del externo
    public void actualizarRoles(Long idUsuario, List<Long> idRoles) {
        repositoryPort.actualizarRoles(idUsuario, idRoles);

        Usuario usuario = repositoryPort.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", idUsuario));

        if (idRoles != null && idRoles.contains(2L)) {
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

        if (idRoles != null && !idRoles.contains(2L) && usuario.getIdPersona() != null) {
            asesorRepositoryPort.findByIdPersona(usuario.getIdPersona())
                    .ifPresent(a -> asesorRepositoryPort.darBaja(a.getIdAsesor()));
        }
    }

    @Transactional // ✅ adaptado del externo
    public void darBajaUsuario(Long idUsuario) {
        repositoryPort.darBaja(idUsuario);
    }

    // ── HELPERS PRIVADOS ─────────────────────────────────────────────────────

    // ✅ adaptado del externo — verifica rol sin inyectar SecurityContext
    private boolean tieneRol(Long idUsuario, Long idRol) {
        Usuario u = repositoryPort.findById(idUsuario).orElse(null);
        if (u == null || u.getIdRoles() == null) return false;
        return u.getIdRoles().contains(idRol);
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

        if (dom.getIdPersona() != null) {
            personaRepositoryPort.findById(dom.getIdPersona()).ifPresent(persona -> {
                String nombreCompleto = List.of(
                    persona.getNombre()          == null ? "" : persona.getNombre().trim(),
                    persona.getApellidoPaterno() == null ? "" : persona.getApellidoPaterno().trim(),
                    persona.getApellidoMaterno() == null ? "" : persona.getApellidoMaterno().trim()
                ).stream()
                 .filter(s -> !s.isEmpty())
                 .reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);

                if (!nombreCompleto.isEmpty()) dto.setNombreCompleto(nombreCompleto);
            });
        }

        return dto;
    }
// ── Actualizar usuario ────────────────────────────────────────────────
@Transactional
public UsuarioDTO actualizar(Long id, ActualizarUsuarioRequestDTO request, String loginSolicitante) {
    Usuario usuario = repositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

    // Actualiza persona si hay campos de nombre/correo
    if (usuario.getIdPersona() != null) {
        personaRepositoryPort.findById(usuario.getIdPersona()).ifPresent(persona -> {
            if (request.getNombre()          != null) persona.setNombre(request.getNombre());
            if (request.getApellidoPaterno() != null) persona.setApellidoPaterno(request.getApellidoPaterno());
            if (request.getApellidoMaterno() != null) persona.setApellidoMaterno(request.getApellidoMaterno());
            if (request.getCorreo()          != null) persona.setCorreo(request.getCorreo());
            personaRepositoryPort.save(persona);
        });
    }

    if (request.getIdArea()  != null) usuario.setIdArea(request.getIdArea().longValue());
    if (request.getCorreo()  != null) usuario.setCorreoElectronico(request.getCorreo());
    if (request.getPassword() != null && !request.getPassword().isBlank()) {
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    return mapToResponse(repositoryPort.save(usuario));
}

// ── Dar de baja ───────────────────────────────────────────────────────
@Transactional
public void darBaja(Long id) {
    repositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    repositoryPort.darBaja(id);
}
    
}

 

