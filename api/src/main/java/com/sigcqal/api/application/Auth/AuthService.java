package com.sigcqal.api.application.Auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sigcqal.api.infra.Auth.Entity.RefreshTokenEntity;
import com.sigcqal.api.infra.Auth.Repository.RefreshTokenRepository; // ← necesitas este repo
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

@Service
public class AuthService {

    private final UsuarioJpaRepository  usuarioRepo;
    private final UsuarioRolRepository  usuarioRolRepo;
    private final UserSessionRepository sessionRepo;
    private final RefreshTokenRepository refreshTokenRepo; // ← NUEVO
    private final PasswordEncoder       passwordEncoder;
    private final TokenProvider         tokenProvider;
    private final SessionService        sessionService;

    public AuthService(
            UsuarioJpaRepository  usuarioRepo,
            UsuarioRolRepository  usuarioRolRepo,
            UserSessionRepository sessionRepo,
            RefreshTokenRepository refreshTokenRepo, // ← NUEVO
            PasswordEncoder       passwordEncoder,
            TokenProvider         tokenProvider,
            SessionService        sessionService) {
        this.usuarioRepo       = usuarioRepo;
        this.usuarioRolRepo    = usuarioRolRepo;
        this.sessionRepo       = sessionRepo;
        this.refreshTokenRepo  = refreshTokenRepo; // ← NUEVO
        this.passwordEncoder   = passwordEncoder;
        this.tokenProvider     = tokenProvider;
        this.sessionService    = sessionService;
    }

    // ── Login ─────────────────────────────────────────────────────────────
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

        // 5. Generar tokens
        String accessToken  = tokenProvider.generateAccessToken(usuario, roles);
        String refreshToken = tokenProvider.generateRefreshToken(usuario);

        // 6. Control de sesiones concurrentes (máx 1)
        sessionService.enforceSessionLimit(usuario.getId().intValue(), 1);
        sessionService.createSession(usuario.getId().intValue(), accessToken, ip, ua);

        // 7. ← NUEVO: persistir refresh token en BD
        //    Revocar cualquier refresh token anterior del usuario antes de guardar el nuevo
        refreshTokenRepo.revokeAllByUser(usuario);

        RefreshTokenEntity refreshEntity = new RefreshTokenEntity();
        refreshEntity.setToken(refreshToken);
        refreshEntity.setUser(usuario);
        // La expiración coincide con refreshTokenExpirationMs del TokenProvider
        // TokenProvider no expone ese valor directamente, así que lo calculamos igual:
        // Si tienes acceso a AppProperties aquí, úsalo; si no, 7 días es el valor típico
        refreshEntity.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshEntity.setRevoked(false);
        refreshTokenRepo.save(refreshEntity);

        return buildResponse(usuario, accessToken, refreshToken, roles);
    }

    // ── Refresh token ─────────────────────────────────────────────────────
    @Transactional
    public AuthResponse refresh(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Refresh token inválido o expirado");
        }

        // ← NUEVO: verificar que el refresh token esté en BD y no revocado
        RefreshTokenEntity stored = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("Refresh token no encontrado"));

        if (Boolean.TRUE.equals(stored.getRevoked())) {
            throw new BadCredentialsException("Refresh token revocado");
        }

        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Refresh token expirado");
        }

        String username = tokenProvider.extractUsername(refreshToken);
        UsuarioEntity usuario = usuarioRepo.findByUsuarioLogin(username)
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        List<UsuarioRolEntity> roles = usuarioRolRepo.findByUsuario_Id(usuario.getId());
        String nuevoAccessToken  = tokenProvider.generateAccessToken(usuario, roles);
        String nuevoRefreshToken = tokenProvider.generateRefreshToken(usuario);

        // Revocar el refresh token usado y guardar el nuevo
        stored.setRevoked(true);
        refreshTokenRepo.save(stored);

        RefreshTokenEntity nuevoRefreshEntity = new RefreshTokenEntity();
        nuevoRefreshEntity.setToken(nuevoRefreshToken);
        nuevoRefreshEntity.setUser(usuario);
        nuevoRefreshEntity.setExpiresAt(LocalDateTime.now().plusDays(7));
        nuevoRefreshEntity.setRevoked(false);
        refreshTokenRepo.save(nuevoRefreshEntity);

        return buildResponse(usuario, nuevoAccessToken, nuevoRefreshToken, roles);
    }

    // ── Logout ────────────────────────────────────────────────────────────
    @Transactional
    public void logout(String token) {
        String username = tokenProvider.extractUsername(token);
        usuarioRepo.findByUsuarioLogin(username)
                .ifPresent(usuario -> {
                    sessionService.revokeAllActiveSessions(usuario);
                    // ← NUEVO: revocar también los refresh tokens al hacer logout
                    refreshTokenRepo.revokeAllByUser(usuario);
                });
    }

    // ── Register ──────────────────────────────────────────────────────────
    public void register(RegisterRequest req) {
        if (usuarioRepo.existsByUsuarioLogin(req.usuarioLogin())) {
            throw new IllegalArgumentException("El login ya existe");
        }
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
                    m.put("urlBase",   "");
                    return m;
                })
                .collect(Collectors.toList());
    }
}