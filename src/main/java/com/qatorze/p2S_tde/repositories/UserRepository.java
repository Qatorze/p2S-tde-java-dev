package com.qatorze.p2S_tde.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.qatorze.p2S_tde.models.User;

/**
 * Repository pour accéder aux données des utilisateurs dans la base de données.
 * Cette interface étend JpaRepository, ce qui permet d'utiliser les fonctionnalités
 * de Spring Data JPA pour gérer les entités "User".
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 // Metodo per trovare un utente tramite il token di reset della password
    Optional<User> findByPasswordResetToken(String token);
    
    /**
     * Recherche un utilisateur par son adresse email.
     * @param email l'adresse email de l'utilisateur.
     * @return un Optional contenant l'utilisateur trouvé, ou vide si aucun utilisateur n'est trouvé.
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Recherche un utilisateur par son nom.
     * @param surname le nom de l'utilisateur.
     * @return un Optional contenant l'utilisateur trouvé, ou vide si aucun utilisateur n'est trouvé.
     */
    Optional<User> findBySurname(String surname);

    /**
     * Compte le nombre total d'utilisateurs dans la base de données.
     * @return le nombre total d'utilisateurs.
     */
    long count();
}