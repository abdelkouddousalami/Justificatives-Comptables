package org.example.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.security.dto.JwtResponse;
import org.example.security.dto.LoginRequest;
import org.example.security.entity.Utilisateur;
import org.example.security.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotDePasse())
        );

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(utilisateur);

        return ResponseEntity.ok(new JwtResponse(jwt, utilisateur.getEmail(), utilisateur.getRole().name()));
    }
}