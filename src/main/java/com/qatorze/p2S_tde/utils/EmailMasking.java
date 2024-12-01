package com.qatorze.p2S_tde.utils;

public class EmailMasking {
	
	/**
     * Masque une adresse email en gardant visibles les 3 premiers caractères
     * du nom d'utilisateur et en remplaçant le reste par des astérisques.
     * @param email L'adresse email à masquer.
     * @return L'adresse email masquée.
     * @throws IllegalArgumentException Si l'email est nul ou mal formaté.
     */
	
	public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format."); // Vérifie la validité de l'email.
        }
        
        String[] parts = email.split("@"); // Sépare le nom d'utilisateur et le domaine.
        String username = parts[0];
        String domain = parts[1];
        
        // Garde visibles les 3 premiers caractères du nom d'utilisateur.
        int visibleChars = Math.min(3, username.length());
        String visiblePart = username.substring(0, visibleChars);
        
        // Remplace le reste par des astérisques.
        String maskedUsername = visiblePart + "*".repeat(username.length() - visibleChars);
        
        return maskedUsername + "@" + domain; // Combine le nom masqué avec le domaine.
    }
}

