package com.sigcqal.api.application.Auth;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.infra.Auth.Entity.UserSessionEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository.UserSessionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepository sessionRepo;
    private final UsuarioJpaRepository  userRepo;

    public void createSession(Integer userId, String sessionId, String ip, String ua) {
        UserSessionEntity session = new UserSessionEntity();
        UsuarioEntity user = userRepo.getReferenceById(userId.longValue());
        session.setUserId(user);
        session.setSessionId(sessionId);
        session.setIpAddress(ip);
        session.setUserAgent(ua);
        session.setLastActive(LocalDateTime.now());
        session.setIsActive(true);
        sessionRepo.save(session);
    }

    @Transactional
    public void enforceSessionLimit(Integer userId, int maxSessions) {
        List<UserSessionEntity> active =
            sessionRepo.findActiveSessionsByUserId(userId.longValue()); // ← Long
        if (active.size() >= maxSessions) {
            active.stream()
                .min((a, b) -> a.getLastActive().compareTo(b.getLastActive()))
                .ifPresent(s -> {
                    s.setIsActive(false);
                    sessionRepo.save(s);
                });
        }
    }

    @Transactional
    public void revokeAllActiveSessions(UsuarioEntity user) {
        List<UserSessionEntity> active =
            sessionRepo.findActiveSessionsByUserId(user.getId()); // ← ya es Long
        active.forEach(s -> s.setIsActive(false));
        sessionRepo.saveAll(active);
    }
}