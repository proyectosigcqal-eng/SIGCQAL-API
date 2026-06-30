package com.sigcqal.api.web.Auth.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.Auth.AuthService;
import com.sigcqal.api.web.Auth.Dto.LoginRequestDTO;
import com.sigcqal.api.web.Auth.Dto.LoginResponseDTO;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(service.login(request));
    }
}