package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsque les identifiants (email ou mot de passe) sont invalides.
 */
public class InvalidCredentialsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L; // Requis pour la sérialisation.
	
	/**
     * Constructeur par défaut avec un message d'erreur prédéfini.
     */
    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }
}