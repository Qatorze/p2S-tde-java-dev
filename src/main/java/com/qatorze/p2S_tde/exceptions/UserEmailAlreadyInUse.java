package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsqu'une adresse email est déjà utilisée par un autre utilisateur.
 */
public class UserEmailAlreadyInUse extends RuntimeException {
    
    private static final long serialVersionUID = 1L; // Requis pour la sérialisation.

    /**
     * Constructeur par défaut avec un message d'erreur prédéfini.
     */
    public UserEmailAlreadyInUse() {
        super("Email already in use.");
    }
}