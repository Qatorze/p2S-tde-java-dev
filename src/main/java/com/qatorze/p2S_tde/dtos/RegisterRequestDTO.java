package com.qatorze.p2S_tde.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la requête d'enregistrement. 
 * Contient les champs nécessaires pour créer un nouvel utilisateur.
 */
public class RegisterRequestDTO {

    @NotBlank(message = "Le nom ne peut pas etre vide") // Vérifie que le champ n'est pas vide.
    @Size(max = 20, message = "Le nom doit etre long de 20 caractères max") // Limite la longueur du champ.
    private String surname;

    @NotBlank(message = "Le prenom ne peut pas etre vide")
    @Size(max = 20, message = "Le prenom doit etre long de 20 caractères max")
    private String name;

    @NotBlank(message = "L'email ne peut pas etre vide")
    @Email(message = "L'email doit etre valide")
    @Size(max = 30, message = "L'email doit etre lond de 30 caractères max")
    private String email;

    @NotBlank(message = "La password ne peut pas etre vide")
    @Size(min = 8, message = "La password doit contenir au moins 8 caractères")
    private String password;

    public RegisterRequestDTO() {} // Constructeur par défaut.

    // Constructeur avec paramètres.
    public RegisterRequestDTO(String surname, String name, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters et setters.
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

