package com.qatorze.p2S_tde.restcontroller;

import com.qatorze.p2S_tde.dtos.PasswordResetRequestDTO;
import com.qatorze.p2S_tde.dtos.PasswordResetDTO;
import com.qatorze.p2S_tde.services.PasswordResetService;
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
    public ResponseEntity<String> requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        try {
            passwordResetService.initiatePasswordReset(request);
            return ResponseEntity.ok("Lien de réinitialisation de mot de passe envoyé à votre email");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Point de terminaison pour effectuer la réinitialisation du mot de passe avec le token.
     * Reçoit un token et le nouveau mot de passe pour mettre à jour celui de l'utilisateur.
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        try {
            passwordResetService.resetPassword(resetDTO);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
