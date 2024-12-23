package com.qatorze.p2S_tde.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qatorze.p2S_tde.dtos.ChangePasswordRequestDTO;
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
        System.out.println("JWT Token généré : " + token); // Log du token JWT

        // Stocke le token dans un cookie HttpOnly
        setTokenInCookie(response, token);

        // Génère et stocke un token CSRF séparé
        String csrfToken = jwtService.generateCsrfToken();
        System.out.println("CSRF Token généré : " + csrfToken); // Log du token CSRF
        setCsrfTokenInCookie(response, csrfToken);
        
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
        System.out.println("JWT Token généré pour l'enregistrement : " + token); // Log du token JWT

        // Stocke le token dans un cookie HttpOnly
        setTokenInCookie(response, token);

        // Génère et stocke un token CSRF séparé
        String csrfToken = jwtService.generateCsrfToken();
        System.out.println("CSRF Token généré pour l'enregistrement : " + csrfToken); // Log du token CSRF
        setCsrfTokenInCookie(response, csrfToken);
        
        return ResponseEntity.ok(newUserDTO);
    }

    /**
     * Endpoint pour changer le mot de passe de l'utilisateur.
     */
    @PostMapping("/password-change")
    public ResponseEntity<Map<String, Object>> changePassword(
            @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO,
            @RequestHeader("X-CSRF-TOKEN") String csrfToken) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verifica il token CSRF
            if (!jwtService.validateCsrfToken(csrfToken)) {
                response.put("error", "Invalid CSRF token");
                return ResponseEntity.status(403).body(response);
            }

            // Cambia la password tramite il servizio
            authService.changePassword(
                    changePasswordRequestDTO.getEmail(),
                    changePasswordRequestDTO.getOldPassword(),
                    changePasswordRequestDTO.getNewPassword()
            );

            response.put("message", "Password changed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    /**
     * Définit un cookie pour le token JWT.
     */
    private void setTokenInCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("p2s_tde_jw_token", token);
        cookie.setHttpOnly(true);  // Rendere il cookie non accessibile tramite JavaScript (protezione contro XSS).
        cookie.setSecure(true);    // passare poi a true - Solo su HTTPS (utile in produzione).
        cookie.setPath("/");       // Il cookie è disponibile su tutto il dominio.
        cookie.setMaxAge(24 * 60 * 60); // Durata di 24 ore.

        // Aggiungi manualmente l'intestazione Set-Cookie
        response.addHeader("Set-Cookie", String.format(
        		"%s=%s; Max-Age=%d; Path=%s; HttpOnly; Secure; SameSite=Strict",
        		//"%s=%s; Max-Age=%d; Path=%s; HttpOnly; SameSite=Strict",
                cookie.getName(),
                cookie.getValue(),
                cookie.getMaxAge(),
                cookie.getPath()
         ));
    }

    /**
     * Définit un cookie pour le token CSRF.
     */
    private void setCsrfTokenInCookie(HttpServletResponse response, String csrfToken) {
        Cookie csrfCookie = new Cookie("p2s_tde_csrf_token", csrfToken);

        // Imposta i parametri per il cookie.
        csrfCookie.setHttpOnly(false);  // Il token CSRF non deve essere HttpOnly (perché deve essere inviato dal client). Doit être accessible via JavaScript
        csrfCookie.setSecure(false);     // passare poi a true - Solo su HTTPS (utile in produzione).
        csrfCookie.setPath("/");        // Imposta il percorso del cookie (disponibile per tutta l'app).
        csrfCookie.setMaxAge(24 * 60 * 60); // Durata di 24 ore.

        // Aggiungi manualmente l'intestazione Set-Cookie
        response.addHeader("Set-Cookie", String.format(
        		//"%s=%s; Max-Age=%d; Path=%s; Secure; SameSite=Strict",
                "%s=%s; Max-Age=%d; Path=%s; SameSite=Lax",
                csrfCookie.getName(),
                csrfCookie.getValue(),
                csrfCookie.getMaxAge(),
                csrfCookie.getPath()
            ));
    }

}
