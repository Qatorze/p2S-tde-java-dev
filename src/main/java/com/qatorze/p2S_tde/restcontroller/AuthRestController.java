package com.qatorze.p2S_tde.restcontroller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qatorze.p2S_tde.dtos.LoginRequestDTO;
import com.qatorze.p2S_tde.dtos.RegisterRequestDTO;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.services.AuthService;
import com.qatorze.p2S_tde.services.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Auth", description = "Endpoint pour la gestion de l'authentification")
@RestController // Annotation pour déclarer cette classe comme un contrôleur REST.
@RequestMapping("/api/auth") // Préfixe pour les endpoints d'authentification.
public class AuthRestController {

    @Autowired
    private AuthService authService; // Injection du service d'authentification.
    
    @Autowired
    private JwtService jwtService; // Injection du service de gestion des JWT.

    /**
     * Endpoint pour connecter un utilisateur.
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
    	// Appelle le service pour authentifier l'utilisateur.
        UserResponseDTO userResponse = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        // Génère un token JWT pour l'utilisateur authentifié.
        String token = jwtService.generateToken(userResponse);
        // Stocke le token dans un cookie.
        setTokenInCookie(response, token);
        // Ajoute le token à la réponse (optionnel, pour debug ou cas spécifiques).
        userResponse.setToken(token);
        return ResponseEntity.ok(userResponse);
    }
    
    
    /**
     * Endpoint pour enregistrer un nouvel utilisateur.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO, HttpServletResponse response) {
    	// Appelle le service pour créer un nouvel utilisateur.
    	UserResponseDTO newUserDTO = authService.register(registerRequestDTO);
    	// Génère un token JWT pour le nouvel utilisateur.
        String token = jwtService.generateToken(newUserDTO);
        // Stocke le token dans un cookie.
        setTokenInCookie(response, token);
        // Retourne l'utilisateur enregistré avec son token.
        newUserDTO.setToken(token);
        return ResponseEntity.ok(newUserDTO);
    }
    
    /**
     * Définit un cookie pour le token JWT.
     */
    private void setTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("p2s_tde_JSON_Web_Token", token);
        cookie.setHttpOnly(true); // Rend le cookie inaccessible au JavaScript pour protéger contre les attaques XSS.
        // cookie.setSecure(true); // Utiliser uniquement pour HTTPS (décommenter pour les environnements sécurisés, c'est à dire en production).
        cookie.setPath("/"); // Rendre le cookie accessible sur toute l'application.
        cookie.setMaxAge(24 * 60 * 60); // Définit la durée de validité à 24 heures.
        response.addCookie(cookie); // Ajoute le cookie à la réponse HTTP.
    }
}