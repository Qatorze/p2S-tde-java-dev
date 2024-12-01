package com.qatorze.p2S_tde.mapper;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;	
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.dtos.LoginRequestDTO;
import com.qatorze.p2S_tde.dtos.RegisterRequestDTO;
import com.qatorze.p2S_tde.dtos.UserRequestDTO;
import com.qatorze.p2S_tde.models.User;
import com.qatorze.p2S_tde.utils.EmailMasking;

/**
 * Classe utilitaire pour convertir des entités User en DTO ou vice-versa.
 * Utilisée pour transformer les données selon les besoins des différentes couches de l'application.
 */
@Component
public class UserConverter {

    /**
     * Convertit un objet `User` en `UserResponseDTO`.
     * Ce DTO est destiné à être envoyé au frontend, masquant les données sensibles comme l'email.
     * 
     * @param user L'entité `User` à convertir.
     * @return Un objet `UserResponseDTO` contenant les données nécessaires pour le frontend.
     */
    public UserResponseDTO convertUserToUserResponseDTO(User user) {
        // Masque l'email avant de l'envoyer au frontend.
        String maskedEmail = EmailMasking.maskEmail(user.getEmail());
        
        return new UserResponseDTO(
            user.getId(),
            user.getSurname(),
            user.getName(),
            user.getRole(),
            maskedEmail,
            user.getImagePath()
        );
    }

    /**
     * Convertit un objet `LoginRequestDTO` en une entité `User`.
     * Utile pour les tentatives de connexion, où seules l'email et le mot de passe sont requis.
     * 
     * @param loginRequestDTO Le DTO contenant les informations de connexion.
     * @return Une entité `User` avec les champs pertinents.
     */
    public User convertLoginRequestDTO_ToUser(LoginRequestDTO loginRequestDTO) {
        return new User(
            null, // Surname n'est pas nécessaire pour la connexion
            null, // Name n'est pas nécessaire pour la connexion
            null, // Role n'est pas nécessaire pour la connexion
            loginRequestDTO.getEmail(),
            loginRequestDTO.getPassword(),
            null, // ImagePath n'est pas utilisé ici
            null
        );
    }

    /**
     * Convertit un `RegisterRequestDTO` en une entité `User`.
     * Utile lors de l'inscription d'un nouvel utilisateur, où certains champs comme le rôle 
     * et le chemin de l'image sont définis par défaut ailleurs dans le processus.
     * 
     * @param registerRequestDTO Le DTO contenant les informations d'inscription.
     * @return Une entité `User` avec les champs pertinents pour l'inscription.
     */
    public User convertRegisterRequestDTO_ToUser(RegisterRequestDTO registerRequestDTO) {
        return new User(
            registerRequestDTO.getSurname(),
            registerRequestDTO.getName(),
            null, // Le rôle sera défini dans la méthode de registre du service Auth
            registerRequestDTO.getEmail(),
            registerRequestDTO.getPassword(),
            null,  // Le chemin de l'image sera ajouté après l'inscription
            LocalDateTime.now() // Impostiamo la data di registrazione al momento dell'iscrizione
        );
    }

    /**
     * Convertit un `UserRequestDTO` en une entité `User`.
     * Utile pour la mise à jour des informations d'un utilisateur existant.
     * Certains champs, comme l'email ou le rôle, ne sont pas modifiables via cette méthode.
     * 
     * @param userRequestDTO Le DTO contenant les données de mise à jour.
     * @return Une entité `User` mise à jour.
     */
    public User convertUserRequestDTO_ToUser(UserRequestDTO userRequestDTO) {
        return new User(
            userRequestDTO.getId(), // Inclut l'ID pour identifier l'utilisateur à mettre à jour
            userRequestDTO.getSurname(),
            userRequestDTO.getName(),
            null, // Le rôle n'est pas modifiable directement
            null, // L'email reste inchangé pour des raisons de sécurité
            userRequestDTO.getPassword(), // Permet de mettre à jour le mot de passe
            userRequestDTO.getImagePath(), // Met à jour le chemin de l'image si fourni
            null
        );
    }
}
