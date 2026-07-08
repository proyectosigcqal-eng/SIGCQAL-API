// web/Auth/Dto/LoginRequestDTO.java
package com.sigcqal.api.web.Auth.Dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank String usuarioLogin,
    @NotBlank String password
) {}