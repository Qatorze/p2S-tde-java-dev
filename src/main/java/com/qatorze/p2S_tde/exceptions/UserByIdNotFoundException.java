package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsque l'utilisateur recherché par ID est introuvable.
 */
public class UserByIdNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L; // Requis pour la sérialisation.

    /**
     * Constructeur avec un message personnalisé contenant l'ID introuvable.
     * @param id L'ID de l'utilisateur introuvable.
     */
    public UserByIdNotFoundException(Long id) {
        super("User by id ''" + id + "'' not found.");
    }
}