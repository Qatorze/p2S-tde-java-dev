package com.qatorze.p2S_tde.exceptions;

/**
 * Exception levée lorsque aucune propriété n'est trouvée pour les critères de recherche spécifiés.
 */
public class PropertiesNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Requis pour la sérialisation.

    /**
     * Constructeur avec un message personnalisé pour indiquer qu'aucune propriété n'a été trouvée.
     * 
     * @param criteria Le critère de recherche qui n'a pas abouti.
     */
    public PropertiesNotFoundException(String criteria) {
        super("Aucune propriété trouvée pour les critères : " + criteria);
    }
}
