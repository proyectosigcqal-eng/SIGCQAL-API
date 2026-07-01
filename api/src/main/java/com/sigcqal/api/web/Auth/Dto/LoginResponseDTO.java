package com.sigcqal.api.web.Auth.Dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class LoginResponseDTO {
    private String  token;
    private Integer idUsuario;
    private String  usuarioLogin;
    private Integer idArea;
    private String  nombreArea;
    // 2.8.2 — Lista completa de roles activos
    private List<Map<String, Object>> roles;
}