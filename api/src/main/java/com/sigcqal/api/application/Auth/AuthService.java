package com.sigcqal.api.application.Auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UserSessionRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UsuarioRolRepository;
import com.sigcqal.api.security.TokenProvider;
import com.sigcqal.api.web.Auth.Dto.AuthResponse;
import com.sigcqal.api.web.Auth.Dto.LoginRequestDTO;
import com.sigcqal.api.web.Auth.Dto.RegisterRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class AuthService {


    private final UsuarioJpaRepository  usuarioRepo;
    private final UsuarioRolRepository  usuarioRolRepo;
    private final UserSessionRepository sessionRepo;
    private final PasswordEncoder       passwordEncoder;
    private final TokenProvider         tokenProvider;
    private final SessionService        sessionService;

    public AuthService(
            UsuarioJpaRepository  usuarioRepo,
            UsuarioRolRepository  usuarioRolRepo,
            UserSessionRepository sessionRepo,
            PasswordEncoder       passwordEncoder,
            TokenProvider         tokenProvider,
            SessionService  sessionService) {  // ← @Lazy aquí
        this.usuarioRepo    = usuarioRepo;
        this.usuarioRolRepo = usuarioRolRepo;
        this.sessionRepo    = sessionRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider  = tokenProvider;
        this.sessionService = sessionService;
    }

    // ── 2.8.1 Login estricto ─────────────────────────────────────────────
    @Transactional
    public AuthResponse login(LoginRequestDTO req, String ip, String ua) {

        // 1. Buscar usuario
        UsuarioEntity usuario = usuarioRepo.findByUsuarioLogin(req.usuarioLogin())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        // 2. Validar activo
        if (Boolean.FALSE.equals(usuario.getActivo())) {
            throw new BadCredentialsException("Usuario inactivo");
        }

        // 3. Validar password
        if (!passwordEncoder.matches(req.password(), usuario.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // 4. Obtener roles activos
        List<UsuarioRolEntity> roles = usuarioRolRepo.findByUsuario_Id(usuario.getId());

        // 5. Generar tokens — 2.8.2 payload incluye roles
        String accessToken  = tokenProvider.generateAccessToken(usuario, roles);
        String refreshToken = tokenProvider.generateRefreshToken(usuario);

        // 6. Control de sesiones concurrentes (máx 1)
        sessionService.enforceSessionLimit(usuario.getId().intValue(), 1);
        sessionService.createSession(usuario.getId().intValue(), accessToken, ip, ua);

        return buildResponse(usuario, accessToken, refreshToken, roles);
    }

    // ── Refresh token ─────────────────────────────────────────────────────
    @Transactional
    public AuthResponse refresh(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Refresh token inválido o expirado");
        }

        String username = tokenProvider.extractUsername(refreshToken);
        UsuarioEntity usuario = usuarioRepo.findByUsuarioLogin(username)
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        List<UsuarioRolEntity> roles = usuarioRolRepo.findByUsuario_Id(usuario.getId());
        String nuevoToken = tokenProvider.generateAccessToken(usuario, roles);

        // Actualizar sessionId en BD
        sessionRepo.findBySessionId(refreshToken)
                .ifPresent(s -> {
                    s.setSessionId(nuevoToken);
                    sessionRepo.save(s);
                });

        return buildResponse(usuario, nuevoToken, refreshToken, roles);
    }

    // ── Logout ────────────────────────────────────────────────────────────
    @Transactional
    public void logout(String token) {
        String username = tokenProvider.extractUsername(token);
        usuarioRepo.findByUsuarioLogin(username)
                .ifPresent(sessionService::revokeAllActiveSessions);
    }

    // ── Register ──────────────────────────────────────────────────────────
    public void register(RegisterRequest req) {
        if (usuarioRepo.existsByUsuarioLogin(req.usuarioLogin())) {
            throw new IllegalArgumentException("El login ya existe");
        }
        // Lógica de registro si se necesita
    }

    // ── Helpers privados ──────────────────────────────────────────────────

    private AuthResponse buildResponse(
            UsuarioEntity usuario,
            String accessToken,
            String refreshToken,
            List<UsuarioRolEntity> roles) {

        Integer idArea    = usuario.getIdArea() != null
                            ? usuario.getIdArea().getId().intValue() : null;
        String nombreArea = usuario.getIdArea() != null
                            ? usuario.getIdArea().getNombre()        : null;

        return new AuthResponse(
            accessToken,
            refreshToken,
            usuario.getId().intValue(),
            usuario.getUsuarioLogin(),
            idArea,
            nombreArea,
            buildRolesPayload(roles)
        );
    }

    private List<Map<String, Object>> buildRolesPayload(List<UsuarioRolEntity> roles) {
    return roles.stream()
            .map(r -> {
                Map<String, Object> m = new HashMap<>();
                m.put("idRol",     r.getRol().getId());
                m.put("nombreRol", r.getRol().getNombre());
                m.put("urlBase",   ""); // ← RolEntity no tiene url, se deja vacío
                return m;
            })
            .collect(Collectors.toList());
}
}