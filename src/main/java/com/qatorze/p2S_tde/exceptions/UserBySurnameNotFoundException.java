package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsque l'utilisateur recherché par surname est introuvable.
 */
public class UserBySurnameNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L; // Requis pour la sérialisation.

    /**
     * Constructeur avec un message personnalisé contenant le surname introuvable.
     * @param isurnamed Le surname de l'utilisateur introuvable.
     */
    public UserBySurnameNotFoundException(String surname) {
        super("User with surname ''" + surname + "'' not found.");
    }
}
