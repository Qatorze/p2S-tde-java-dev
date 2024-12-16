package com.qatorze.p2S_tde.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qatorze.p2S_tde.models.User;

@Service
public class PasswordValidatorService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Per confrontare le password codificate

    /**
     * Valide le nouveau mot de passe de l'utilisateur.
     *
     * @param newPassword Le nouveau mot de passe.
     * @param user L'utilisateur pour vérifier que le mot de passe n'est pas dans l'historique.
     * @throws IllegalArgumentException Si le mot de passe est trop court ou identique à un des cinq derniers.
     */
    public void validateNewPassword(String newPassword, User user) {
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères.");
        }

        // Verifica che la nuova password non sia tra le ultime 5
        for (String previousPassword : user.getPreviousPasswords()) {
            if (passwordEncoder.matches(newPassword, previousPassword)) {
                throw new IllegalArgumentException("Le mot de passe ne peut pas être identique à l'un des cinq derniers.");
            }
        }
    }
}
