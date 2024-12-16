package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsque l'utilisateur recherché par email est introuvable.
 */
public class UserByEmailNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L; // Requis pour la sérialisation.

    /**
     * Constructeur avec un message personnalisé contenant l'email introuvable.
     * @param email L'email de l'utilisateur introuvable.
     */
    public UserByEmailNotFoundException(String email) {
        super("User with email ''" + email + "'' not found.");
    }
}