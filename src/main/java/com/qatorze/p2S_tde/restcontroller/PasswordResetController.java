package com.qatorze.p2S_tde.restcontroller;

import com.qatorze.p2S_tde.dtos.PasswordResetRequestDTO;
import com.qatorze.p2S_tde.dtos.PasswordResetDTO;
import com.qatorze.p2S_tde.services.PasswordResetService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Password Reset", description = "Endpoints pour la réinitialisation et la modification du mot de passe")
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    
    /**
     * Point de terminaison pour demander la réinitialisation du mot de passe.
     * Reçoit un email et envoie un email avec le lien pour la réinitialisation.
     */
    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        try {
            passwordResetService.initiatePasswordReset(request);
            
            // Restituisci un oggetto JSON con il messaggio
            Map<String, String> response = new HashMap<>();
            response.put("message", "Lien de réinitialisation de mot de passe envoyé à votre email");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // In caso di errore, restituisci il messaggio di errore come JSON
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    /**
     * Point de terminaison pour effectuer la réinitialisation du mot de passe avec le token.
     * Reçoit un token et le nouveau mot de passe pour mettre à jour celui de l'utilisateur.
     */
    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        try {
        	System.out.println(resetDTO.getToken()+ " "+ resetDTO.getNewPassword());
            passwordResetService.resetPassword(resetDTO);
            
            // Restituisci un oggetto JSON con il messaggio
            Map<String, String> response = new HashMap<>();
            response.put("message", "Mot de passe réinitialisé avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	 // In caso di errore, restituisci il messaggio di errore come JSON
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
