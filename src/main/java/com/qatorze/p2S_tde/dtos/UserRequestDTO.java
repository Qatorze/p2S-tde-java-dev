package com.qatorze.p2S_tde.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la mise à jour d'utilisateur. 
 * Contient uniquement les champs que l'utilisateur peut modifier.
 */
public class UserRequestDTO {

    private Long id; // Identifiant de l'utilisateur.

    @NotBlank(message = "Le nom ne peut pas etre vide")
    @Size(max = 20, message = "Le nom doit etre long de 20 caractères max")
    private String surname;

    @NotBlank(message = "Le prenom ne peut pas etre vide")
    @Size(max = 20, message = "Le prenom doit etre long de 20 caractères max")
    private String name;

    @NotBlank(message = "La password ne peut pas etre vide")
    @Size(min = 8, message = "La password doit contenir au moins 8 caractères")
    private String password;

    private String imagePath; // Chemin de l'image de profil.

    public UserRequestDTO() {} // Constructeur par défaut.

    // Constructeur avec paramètres.
    public UserRequestDTO(Long id, String surname, String name, String password, String imagePath) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.imagePath = imagePath;
    }

    // Getters et setters.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
