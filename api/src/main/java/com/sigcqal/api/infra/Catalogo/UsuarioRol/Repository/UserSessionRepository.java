package com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Auth.Entity.UserSessionEntity;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

    @Query("""
        SELECT s FROM UserSessionEntity s
        WHERE s.userId.id = :userId
          AND s.isActive = true
        """)
    List<UserSessionEntity> findActiveSessionsByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT s FROM UserSessionEntity s
        WHERE s.sessionId = :sessionId
        """)
    Optional<UserSessionEntity> findBySessionId(@Param("sessionId") String sessionId);

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM UserSessionEntity s
        WHERE s.userId.usuarioLogin = :username
          AND s.isActive = true
        """)
    boolean existsByUserUsernameAndIsActiveTrue(@Param("username") String username);
}