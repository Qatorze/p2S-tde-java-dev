package com.qatorze.p2S_tde.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la mise à jour des données utilisateur par l'utilisateur lui-même.
 */
public class UserUpdateBySelfDTO {

    private Long id; // Identifiant de l'utilisateur.

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 20, message = "Le nom doit contenir au maximum 20 caractères")
    private String surname;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @Size(max = 20, message = "Le prénom doit contenir au maximum 20 caractères")
    private String name;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;

    private String imagePath; // Chemin de l'image de profil.

    public UserUpdateBySelfDTO() {}

    public UserUpdateBySelfDTO(Long id, String surname, String name, String password, String imagePath) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.imagePath = imagePath;
    }

    // Getters et setters
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
