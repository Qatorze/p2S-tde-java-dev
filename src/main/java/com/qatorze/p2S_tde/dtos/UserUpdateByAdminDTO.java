package com.qatorze.p2S_tde.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la mise à jour des données utilisateur par un administrateur.
 */
public class UserUpdateByAdminDTO {

    private Long id; // Identifiant de l'utilisateur.

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 20, message = "Le nom doit contenir au maximum 20 caractères")
    private String surname;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @Size(max = 20, message = "Le prénom doit contenir au maximum 20 caractères")
    private String name;

    @NotBlank(message = "Le rôle ne peut pas être vide")
    @Size(max = 20, message = "Le rôle doit contenir au maximum 20 caractères")
    private String role; // Champ spécifique pour l'administrateur.

    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    private String imagePath; // Chemin de l'image de profil.

    public UserUpdateByAdminDTO() {}

    public UserUpdateByAdminDTO(Long id, String surname, String name, String role, String email, String imagePath) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.role = role;
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
