package com.qatorze.p2S_tde.dtos;

/**
 * DTO utilisé pour envoyer une réponse contenant les informations nécessaires sur un utilisateur.
 * Cette classe exclut les données sensibles comme le mot de passe.
 */
public class UserResponseDTO {
    
    private Long id; // Identifiant unique de l'utilisateur.
    private String surname; // Nom de famille de l'utilisateur.
    private String name; // Prénom de l'utilisateur.
    private String role; // Rôle de l'utilisateur (exemple : "user", "admin").
    private String email; // Adresse e-mail de l'utilisateur.
    private String imagePath; // Chemin ou URL de l'image de profil de l'utilisateur.
   

    /**
     * Constructeur par défaut.
     * Nécessaire pour les frameworks comme Spring lors de la désérialisation.
     */
    public UserResponseDTO() {
        super();
    }

    /**
     * Constructeur avec paramètres.
     * @param id Identifiant unique de l'utilisateur.
     * @param surname Nom de famille de l'utilisateur.
     * @param name Prénom de l'utilisateur.
     * @param role Rôle de l'utilisateur.
     * @param email Adresse e-mail de l'utilisateur.
     * @param imagePath Chemin ou URL de l'image de profil de l'utilisateur.
     */
    public UserResponseDTO(Long id, String surname, String name, String role, String email, String imagePath) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.role = role;
        this.email = email;
        this.imagePath = imagePath;
    }

    // Getters et setters pour chaque attribut.

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

