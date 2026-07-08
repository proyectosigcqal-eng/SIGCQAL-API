package com.sigcqal.api.web.Auth.Dto;

import java.util.List;
import java.util.Map;

public record AuthResponse(
    String                    token,
    String                    refreshToken,
    Integer                   idUsuario,
    String                    usuarioLogin,
    Integer                   idArea,
    String                    nombreArea,
    List<Map<String, Object>> roles
) {}