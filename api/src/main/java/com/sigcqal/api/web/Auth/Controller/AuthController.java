package com.sigcqal.api.web.Auth.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sigcqal.api.application.Auth.AuthService;
import com.sigcqal.api.web.Auth.Dto.AuthResponse;
import com.sigcqal.api.web.Auth.Dto.LoginRequestDTO;
import com.sigcqal.api.web.Auth.Dto.LoginResponseDTO;
import com.sigcqal.api.web.Auth.Dto.RefreshRequest;
import com.sigcqal.api.web.Auth.Dto.RegisterRequest;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletRequest httpRequest) {
        String ip = getClientIp(httpRequest);
        String ua = httpRequest.getHeader("User-Agent");
        AuthResponse response = service.login(request, ip, ua);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        AuthResponse response = service.refresh(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        service.logout(token);
        return ResponseEntity.noContent().build();
    }

    // Extrae IP real detrás de proxies (Nginx, AWS ALB, Cloudflare, etc.)
    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        return (xfHeader != null && !xfHeader.isEmpty()) ? xfHeader.split(",")[0].trim() : request.getRemoteAddr();
    }
}
