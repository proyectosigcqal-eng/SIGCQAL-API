package com.sigcqal.api.infra.Auth.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_sessions", schema = "catalogos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionEntity {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UsuarioEntity userId;

@Column(name = "session_id", nullable = false, unique = true, columnDefinition = "TEXT")
private String sessionId;

@Column(name = "ip_address", nullable = false)
private String ipAddress;

@Column(name = "user_agent", nullable = false, length = 500)
private String userAgent;

@Column(name = "last_active", nullable = false)
private LocalDateTime lastActive;

@Column(name = "is_active", nullable = false)
private Boolean isActive;



}
