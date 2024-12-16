package com.qatorze.p2S_tde.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @Column(name = "email", length = 50, nullable = false, unique = true)
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
    
    // Le token de réinitialisation du mot de passe...Ne fait pas partie du constructeur
    @Column(name = "password_reset_token", length = 355, nullable = true)
    private String passwordResetToken;
    
 // Date et heure de création du token de réinitialisation
    @Column(name = "password_reset_token_created_at", nullable = true)
    private LocalDateTime passwordResetTokenCreatedAt;

    
    /**
     * Liste des mots de passe précédents de l'utilisateur.
     * 
     * Cette liste stocke les versions hachées des 5 derniers mots de passe
     * utilisés par l'utilisateur. Elle est utilisée pour garantir que 
     * l'utilisateur ne réutilise pas un mot de passe récent lors de la 
     * réinitialisation ou de la modification de son mot de passe.
     * 
     * Caractéristiques principales :
     * - La liste est persistée en tant qu'élément de collection grâce à 
     *   l'annotation @ElementCollection.
     * - La taille maximale de cette liste est de 5 mots de passe.
     *   Si l'utilisateur change son mot de passe alors que la liste contient 
     *   déjà 5 mots de passe, le mot de passe le plus ancien est supprimé 
     *   pour faire de la place au nouveau mot de passe.
     * - Les mots de passe stockés dans cette liste sont hachés pour des 
     *   raisons de sécurité. Aucun mot de passe en clair n'est jamais enregistré.
     * 
     * Exemple d'utilisation :
     * Lorsqu'un utilisateur modifie son mot de passe, le mot de passe actuel 
     * (haché) est ajouté à cette liste avant de le remplacer par le nouveau 
     * mot de passe haché. Cette liste est ensuite vérifiée pour s'assurer 
     * que le nouveau mot de passe n'a pas été utilisé récemment.
     */
    @ElementCollection
    @CollectionTable(name = "user_previous_passwords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "hashed_password", nullable = false)
    private List<String> previousPasswords = new ArrayList<>();


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
    
    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    
    public LocalDateTime getPasswordResetTokenCreatedAt() {
        return passwordResetTokenCreatedAt;
    }

    public void setPasswordResetTokenCreatedAt(LocalDateTime passwordResetTokenCreatedAt) {
        this.passwordResetTokenCreatedAt = passwordResetTokenCreatedAt;
    }
    
    public List<String> getPreviousPasswords() {
    	return Collections.unmodifiableList(previousPasswords);
    }

    public void setPreviousPasswords(List<String> previousPasswords) {
        this.previousPasswords = previousPasswords;
    }

    /**
     * Ajoute un mot de passe haché à la liste des mots de passe précédents
     * après avoir vérifié qu'il n'est pas déjà dans l'historique.
     * Si la liste atteint 5 mots de passe, le plus ancien est supprimé pour faire
     * de la place au nouveau mot de passe.
     *
     * @param hashedPassword Le nouveau mot de passe haché à ajouter à l'historique.
     * @throws IllegalArgumentException Si le mot de passe est déjà dans l'historique.
     */
    public void addPasswordToHistory(String hashedPassword) {
        if (isPasswordInHistory(hashedPassword)) {
            throw new IllegalArgumentException("Le password non possono essere riutilizzate tra le 5 più recenti.");
        }

        if (this.previousPasswords.size() >= 5) {
            this.previousPasswords.remove(0); // Rimuovi la più vecchia
        }
        this.previousPasswords.add(hashedPassword); // Aggiungi la nuova password
    }


    /**
     * Vérifie si un mot de passe haché est présent dans l'historique des mots de passe.
     *
     * @param hashedPassword Le mot de passe haché à vérifier.
     * @return true si le mot de passe est dans l'historique, sinon false.
     */
    public boolean isPasswordInHistory(String hashedPassword) {
        return this.previousPasswords.contains(hashedPassword);
    }
    
    /**
     * Vérifie si le token est encore valide (moins de 24 heures).
     * @return true si le token est valide, sinon false.
     */
    public boolean isPasswordResetTokenValid() {
        if (this.passwordResetTokenCreatedAt == null) {
            return false;
        }
        return !this.passwordResetTokenCreatedAt.isBefore(LocalDateTime.now().minusHours(24));
    }
    
    
    /**
     * Représentation textuelle de l'utilisateur pour les logs.
     * @return une chaîne de caractères contenant toutes les informations de l'utilisateur.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", surname=");
        builder.append(surname);
        builder.append(", name=");
        builder.append(name);
        builder.append(", role=");
        builder.append(role);
        builder.append(", email=");
        builder.append(email);
        builder.append(", password=");
        builder.append("[PROTECTED]");
        builder.append(", imagePath=");
        builder.append(imagePath);
        builder.append(", registrationDate=");
        builder.append(registrationDate);
        builder.append(", passwordResetToken=");
        builder.append("[PROTECTED]");
        builder.append("]");
        return builder.toString();
    }
    
}
