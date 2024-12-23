package com.qatorze.p2S_tde.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${csrf.secret.key}")
    private String CSRF_SECRET_KEY;

    public String generateToken(UserResponseDTO user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 ore
                .withClaim("id", user.getId())
                .withClaim("surname", user.getSurname())
                .withClaim("name", user.getName())
                .withClaim("role", user.getRole())
                .withClaim("email", user.getEmail())
                .withClaim("imagePath", user.getImagePath())
                .sign(algorithm);
    }

    public UserResponseDTO validateTokenAndGetUser(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        Long id = decodedJWT.getClaim("id").asLong();
        String surname = decodedJWT.getClaim("surname").asString();
        String name = decodedJWT.getClaim("name").asString();
        String role = decodedJWT.getClaim("role").asString();
        String email = decodedJWT.getClaim("email").asString();
        String imagePath = decodedJWT.getClaim("imagePath").asString();

        return new UserResponseDTO(id, surname, name, role, email, imagePath);
    }

    /**
     * Genera un token CSRF utilizzando un identificativo univoco.
     */
    public String generateCsrfToken() {
        Algorithm algorithm = Algorithm.HMAC256(CSRF_SECRET_KEY);
        return JWT.create()
                .withSubject("CSRF-TOKEN")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 ore
                .withClaim("csrf", UUID.randomUUID().toString())
                .sign(algorithm);
    }

    /**
     * Valida il token CSRF ricevuto.
     */
    public boolean validateCsrfToken(String csrfToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(CSRF_SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(csrfToken);
            return true; // Token valido
        } catch (Exception e) {
            return false; // Token non valido
        }
    }
}
