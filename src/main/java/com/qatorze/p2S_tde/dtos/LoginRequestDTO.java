package com.qatorze.p2S_tde.dtos;

import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO utilisé pour la requête de connexion. 
 * Contient uniquement les champs nécessaires pour authentifier l'utilisateur.
 */
public class LoginRequestDTO {

	@NotBlank(message = "L'email ne peut pas etre vide") // Vérifie que le champ n'est pas vide.
    @Email(message = "L'email doit etre valide") // Vérifie que l'email est bien formatée.
    @Size(max = 30, message = "L'email doit etre long de 30 caractères max") // Limite la longueur de l'email.
    private String email;

    @NotBlank(message = "La password ne peut etre vide") // Vérifie que le champ n'est pas vide.
    @Size(min = 8, message = "La password doit avoir au moins 8 caractères") // Vérifie la longueur minimale de la password.
    private String password;

    public LoginRequestDTO() {} // Constructeur par défaut.

    // Constructeur avec paramètres.
	public LoginRequestDTO( String email, String password) {
		this.email = email;
		this.password = password;
	}

	// Getters et setters.
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

