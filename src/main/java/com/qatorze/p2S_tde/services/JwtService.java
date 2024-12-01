package com.qatorze.p2S_tde.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;

@Service // Annotation pour déclarer cette classe comme un service.
public class JwtService {
	
	@Value("${jwt.secret.key}")
	private String SECRET_KEY; // Clé secrète utilisée pour signer les JWT.

	public String generateToken(UserResponseDTO user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // Définit l'algorithme de signature.
        
        /**
         * Génère un token JWT à partir d'un utilisateur.
         */
        return JWT.create()
        		 .withSubject(user.getEmail()) // Définit l'utilisateur comme sujet.
                 .withIssuedAt(new Date()) // Date d'émission.
                 .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expiration dans 24 heures.
                 .withClaim("id", user.getId()) // Ajoute les informations utilisateur dans les claims.
                 .withClaim("surname", user.getSurname())
                 .withClaim("name", user.getName())
                 .withClaim("role", user.getRole())
                 .withClaim("email", user.getEmail())
                 .withClaim("imagePath", user.getImagePath())
                 .sign(algorithm); // Signe le token.
    }

	/**
     * Valide un token JWT et retourne les informations utilisateur.
     */
	public UserResponseDTO validateTokenAndGetUser(String token) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // Même algorithme que pour la génération.
        JWTVerifier verifier = JWT.require(algorithm).build(); // Crée un vérificateur.
        DecodedJWT decodedJWT = verifier.verify(token); // Vérifie et décode le token.

        // Récupère les informations utilisateur des claims.
        Long id = decodedJWT.getClaim("id").asLong();
        String surname = decodedJWT.getClaim("surname").asString();
        String name = decodedJWT.getClaim("name").asString();
        String role = decodedJWT.getClaim("role").asString();
        String email = decodedJWT.getClaim("email").asString();
        String imagePath = decodedJWT.getClaim("imagePath").asString();

        // Retourne un UserResponseDTO avec les données extraites.
        UserResponseDTO user = new UserResponseDTO(id, surname, name, role, email, imagePath);
        return user;
    }
}
