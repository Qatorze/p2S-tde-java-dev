package com.qatorze.p2S_tde.services;

import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.dtos.UserUpdateByAdminDTO;
import com.qatorze.p2S_tde.dtos.UserUpdateBySelfDTO;
import com.qatorze.p2S_tde.exceptions.UserByEmailNotFoundException;
import com.qatorze.p2S_tde.exceptions.UserByIdNotFoundException;
import com.qatorze.p2S_tde.exceptions.UserBySurnameNotFoundException;
import com.qatorze.p2S_tde.mapper.UserConverter;
import com.qatorze.p2S_tde.models.User;
import com.qatorze.p2S_tde.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    /**
     * Récupère tous les utilisateurs depuis la base de données et les convertit en une liste de DTO (Data Transfer Objects).
     * 
     * Étapes du traitement :
     * 1. Récupération des entités `User` depuis le dépôt avec `userRepository.findAll()`.
     *    - Cette méthode retourne une liste d'objets `User` contenant toutes les informations de la base de données.
     * 
     * 2. Conversion des entités `User` en objets `UserResponseDTO` :
     *    - Utilisation de l'API Stream pour traiter chaque élément de la liste de manière fonctionnelle.
     *    - Transformation de chaque `User` en un `UserResponseDTO` grâce au convertisseur `userConverter`.
     * 
     * 3. Collecte des objets transformés :
     *    - Après transformation, les éléments du flux (Stream) sont collectés dans une liste immuable 
     *      à l'aide de la méthode `toList()`.
     * 
     * Explication détaillée des méthodes utilisées :
     * - `stream()` : Crée un flux (`Stream<User>`) à partir de la liste originale `users`. 
     *    Permet de traiter chaque élément séquentiellement ou parallèlement.
     * 
     * - `map()` : Applique une fonction de transformation à chaque élément du flux.
     *    - Ici, chaque élément `User` est transformé en un `UserResponseDTO`.
     *    - Fonction utilisée : `user -> userConverter.convertUserToUserResponseDTO(user)`.
     * 
     * - `toList()` : Collecte tous les éléments du flux transformé dans une nouvelle liste immuable.
     *    - Retourne une liste de type `List<UserResponseDTO>`.
     * 
     * Utilisation :
     * Ce procédé est essentiel pour fournir uniquement les informations nécessaires via les DTO,
     * tout en préservant la sécurité et en respectant le principe de séparation des responsabilités.
     * 
     * @return Une liste de `UserResponseDTO` contenant les informations essentielles des utilisateurs.
     * @throws RuntimeException Si une erreur survient pendant le traitement des utilisateurs.
     */
    public List<UserResponseDTO> getAllUsers() {
        // Étape 1 : Récupérer tous les utilisateurs depuis le dépôt
        // Cette ligne interroge la base de données pour obtenir une liste d'entités User
        List<User> users = userRepository.findAll();

        // Étape 2 : Convertir chaque utilisateur en DTO
        // `stream()` transforme la liste d'origine en un flux
        // `map()` applique la fonction de conversion pour chaque utilisateur
        // `toList()` collecte le résultat dans une nouvelle liste immuable
        List<UserResponseDTO> userResponseDTOs = users.stream()
                                                      .map(user -> userConverter.convertUserToUserResponseDTO(user)) // Conversion
                                                      .toList(); // Collecte en liste immuable

        // Étape 3 : Retourner la liste des DTO
        return userResponseDTOs;
    }

    
    // Methode pour récupérer les details du User à partir de son ID
    public UserResponseDTO getUserById(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userId);
        }
        return userConverter.convertUserToUserResponseDTO(optUser.get());
    }
    
    // Methode pour récupérer les details du User à partir de son surname
    public UserResponseDTO getUserByName(String surname) {
        Optional<User> optUser = userRepository.findBySurname(surname);
        if (optUser.isEmpty()) {
            throw new UserBySurnameNotFoundException(surname);
        }
        return userConverter.convertUserToUserResponseDTO(optUser.get());
    }
    
 // Methode pour récupérer les details du User à partir de son email
    public UserResponseDTO getUserByEmail(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new UserByEmailNotFoundException(email);
        }
        return userConverter.convertUserToUserResponseDTO(optUser.get());
    }


    /**
     * Permet à un administrateur de mettre à jour les informations d'un utilisateur.
     * @param userRequestDTO Les nouvelles informations de l'utilisateur, y compris le rôle.
     * @return Un `UserResponseDTO` contenant les informations mises à jour de l'utilisateur.
     * @throws UserByIdNotFoundException Si l'utilisateur avec l'ID spécifié n'existe pas.
     */
    @Transactional
    public UserResponseDTO updateUserByAdmin(UserUpdateByAdminDTO userUpdateDTO) {
        Optional<User> optUser = userRepository.findById(userUpdateDTO.getId());
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userUpdateDTO.getId());
        }
        User user = optUser.get();

        // Aggiornare tutti i campi permessi
        user.setSurname(userUpdateDTO.getSurname());
        user.setName(userUpdateDTO.getName());
        user.setRole(userUpdateDTO.getRole());
        user.setEmail(userUpdateDTO.getEmail());
        user.setImagePath(userUpdateDTO.getImagePath());

        // Salvare e restituire il DTO aggiornato
        User updatedUser = userRepository.save(user);
        return userConverter.convertUserToUserResponseDTO(updatedUser);
    }


    /**
     * Permet à un utilisateur de mettre à jour ses propres informations.
     * @param userRequestDTO Les nouvelles informations de l'utilisateur.
     * @return Un `UserResponseDTO` contenant les informations mises à jour de l'utilisateur.
     * @throws UserByIdNotFoundException Si l'utilisateur avec l'ID spécifié n'existe pas.
     */
    @Transactional
    public UserResponseDTO updateUserBySelf(UserUpdateBySelfDTO userUpdateDTO) {
        Optional<User> optUser = userRepository.findById(userUpdateDTO.getId());
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userUpdateDTO.getId());
        }
        User user = optUser.get();

        // Aggiornare solo i campi permessi
        user.setSurname(userUpdateDTO.getSurname());
        user.setName(userUpdateDTO.getName());
        user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword())); // Encrypt password
        user.setImagePath(userUpdateDTO.getImagePath());

        // Salvare e restituire il DTO aggiornato
        User updatedUser = userRepository.save(user);
        return userConverter.convertUserToUserResponseDTO(updatedUser);
    }
   
    // Methode pour suprimer un User à partir de son ID
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userId);
        }
        userRepository.delete(optUser.get());
    }
    
   


}
