package com.sigcqal.api.infra.Auth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sigcqal.api.infra.Auth.Entity.RefreshTokenEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;




public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByToken(String refreshToken);
 // JPQL nativo: actualiza en BD sin cargar entidades a memoria (alto rendimiento)
    @Modifying
    @Query("UPDATE RefreshTokenEntity rt SET rt.revoked = true WHERE rt.user = :user AND rt.revoked = false")
    void revokeAllByUser(UsuarioEntity user);
}
