package com.qatorze.p2S_tde.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Représente un utilisateur dans le système.
 * Cette entité est mappée à la table "users" dans la base de données.
 */
@Entity
@Table(name = "users")
public class User {

    // L'identifiant unique de l'utilisateur
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // Le prénom de l'utilisateur
    @Column(name = "surname", length = 20, nullable = false)
    private String surname;

    // Le nom de l'utilisateur
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    // Le rôle de l'utilisateur (ex: admin, utilisateur normal, etc.)
    @Column(name = "role", length = 20, nullable = true)
    private String role;

    // L'email de l'utilisateur, unique dans la base de données
    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    // Le mot de passe de l'utilisateur, stocké sous forme hachée
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    // Le chemin vers l'image de profil de l'utilisateur
    @Column(name = "image_path", length = 255, nullable = true)
    private String imagePath;

    // Date de création de l'utilisateur
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    // Constructeur sans paramètres
    public User() {}

    // Constructeur avec les informations de l'utilisateur
    public User(String surname, String name, String role, String email, String password, String imagePath, LocalDateTime registrationDate) {
        this.surname = surname;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
        this.registrationDate = registrationDate;
    }

    // Constructeur avec tous les champs (y compris l'id)
    public User(Long id, String surname, String name, String role, String email, String password, String imagePath, LocalDateTime registrationDate) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
        this.registrationDate = registrationDate;
    }

    // Getters et setters pour chaque attribut

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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Représentation textuelle de l'utilisateur pour les logs.
     * @return une chaîne de caractères contenant toutes les informations de l'utilisateur.
     */
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", surname='" + surname + '\'' +
               ", name='" + name + '\'' +
               ", role='" + role + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", imagePath='" + imagePath + '\'' +
               ", registrationDate=" + registrationDate +
               '}';
    }
}
