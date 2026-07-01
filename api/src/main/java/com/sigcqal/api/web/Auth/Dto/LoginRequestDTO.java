package com.sigcqal.api.web.Auth.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank private String usuarioLogin;
    @NotBlank private String password;
}