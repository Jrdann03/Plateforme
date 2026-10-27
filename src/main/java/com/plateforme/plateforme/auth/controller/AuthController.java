package com.plateforme.plateforme.auth.controller;

import com.plateforme.plateforme.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<String> generateTestToken(@RequestParam String identifier) {
        // Génère un JWT pour l'email ou le matricule fourni (sans vérification en base)
        String token = jwtService.generateToken(identifier);
        return ResponseEntity.ok(token);
    }
}