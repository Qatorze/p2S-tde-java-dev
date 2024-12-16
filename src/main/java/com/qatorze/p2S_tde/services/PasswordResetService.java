package com.qatorze.p2S_tde.services;

import com.qatorze.p2S_tde.dtos.PasswordResetRequestDTO;
import com.qatorze.p2S_tde.dtos.PasswordResetDTO;
import com.qatorze.p2S_tde.exceptions.UserByEmailNotFoundException;
import com.qatorze.p2S_tde.models.User;
import com.qatorze.p2S_tde.repositories.UserRepository;
import com.qatorze.p2S_tde.utils.EmailSender;
import com.qatorze.p2S_tde.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

/**
 * Service qui gère les opérations liées aux mots de passe :
 * 1. Réinitialisation de mot de passe via un token (reset).
 * 2. Modification de mot de passe pour un utilisateur authentifié.
 */
@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private PasswordValidatorService passwordValidatorService; // Iniezione del servizio di validazione


    /**
     * Lance le processus de réinitialisation du mot de passe.
     *
     * @param passwordResetRequestDTO Objet contenant l'email de l'utilisateur.
     * @throws UserByEmailNotFoundException Si aucun utilisateur n'est trouvé avec l'email fourni.
     */
    @Transactional
    public void initiatePasswordReset(PasswordResetRequestDTO passwordResetRequestDTO) {
        Optional<User> optUser = userRepository.findByEmail(passwordResetRequestDTO.getEmail());
        if (optUser.isEmpty()) {
            throw new UserByEmailNotFoundException(passwordResetRequestDTO.getEmail());
        }

        User user = optUser.get();
        String resetToken = tokenGenerator.generateResetToken();
        String encodedToken = Base64.getEncoder().encodeToString(resetToken.getBytes());

        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        String resetLink = "http://localhost:8082/reset-password?token=" + encodedToken;
        try {
            emailSender.sendEmail(user.getEmail(), "Demande de réinitialisation de mot de passe",
                    "Bonjour " + user.getName() + ",\n\n" +
                            "Cliquez sur le lien pour réinitialiser votre mot de passe :\n" +
                            resetLink + "\n\n" +
                            "Cordialement,\nL'équipe de support.");
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email.");
        }
    }

    /**
     * Réinitialise le mot de passe en utilisant un token de réinitialisation.
     *
     * @param passwordResetDTO Objet contenant le token et le nouveau mot de passe.
     * @throws IllegalArgumentException Si le token est invalide ou expiré.
     */
    public void resetPassword(PasswordResetDTO passwordResetDTO) {
        User user = validateAndRetrieveUserByToken(passwordResetDTO.getToken());
       
        passwordValidatorService.validateNewPassword(passwordResetDTO.getNewPassword(), user);

        String hashedNewPassword = passwordEncoder.encode(passwordResetDTO.getNewPassword());
        user.addPasswordToHistory(hashedNewPassword);
        user.setPassword(hashedNewPassword);

        user.setPasswordResetToken(null);
        user.setPasswordResetTokenCreatedAt(null);
        userRepository.save(user);

        sendPasswordChangedNotification(user);
    }

    /**
     * Valide et récupère l'utilisateur à partir du token de réinitialisation.
     *
     * @param token Le token de réinitialisation.
     * @return L'utilisateur associé au token.
     * @throws IllegalArgumentException Si le token est invalide ou expiré.
     */
    private User validateAndRetrieveUserByToken(String token) {
        String decodedToken;
        User user;

        try {
            // Décodage du token Base64
            decodedToken = new String(Base64.getDecoder().decode(token));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Le token est invalide : erreur de décodage.");
        }

        // Récupération de l'utilisateur associé au token
        Optional<User> optionalUser = userRepository.findByPasswordResetToken(decodedToken);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Le token est invalide : aucun utilisateur trouvé.");
        }

        user = optionalUser.get();

        // Vérification de la validité temporelle du token
        LocalDateTime tokenCreationTime = user.getPasswordResetTokenCreatedAt();
        if (tokenCreationTime == null || tokenCreationTime.isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new IllegalArgumentException("Le token est expiré. Veuillez en générer un nouveau.");
        }

        return user;
    }

    /**
     * Envoie une notification à l'utilisateur après la modification de son mot de passe.
     *
     * @param user L'utilisateur dont le mot de passe a été changé.
     */
    private void sendPasswordChangedNotification(User user) {
        String loginUrl = "http://localhost:4200/auth/login";
        String subject = "Votre mot de passe a été modifié avec succès";
        String body = "Bonjour " + user.getName() + ",\n\n" +
                      "Votre mot de passe a été modifié avec succès.\n" +
                      "Pour vous connecter : " + loginUrl + "\n\n" +
                      "Cordialement,\nL'équipe de support.";
        try {
            emailSender.sendEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la notification.");
        }
    }
}
