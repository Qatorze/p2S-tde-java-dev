package com.qatorze.p2S_tde.restcontroller;

import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.dtos.UserUpdateByAdminDTO;
import com.qatorze.p2S_tde.dtos.UserUpdateBySelfDTO;
import com.qatorze.p2S_tde.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les utilisateurs.
 * Fournit des endpoints pour mettre à jour, rechercher et supprimer des utilisateurs.
 */
@Tag(name = "Users", description = "Endpoint pour la gestion des utilisateurs")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint pour mettre à jour un utilisateur par un administrateur.
     * @param userUpdateDTO Un objet DTO contenant les données de mise à jour de l'utilisateur.
     * @return Un objet `UserResponseDTO` contenant les données mises à jour de l'utilisateur.
     */
    @PutMapping("/update/admin")
    public ResponseEntity<UserResponseDTO> updateUserByAdmin(@RequestBody UserUpdateByAdminDTO userUpdateDTO) {
        UserResponseDTO updatedUserDTO = userService.updateUserByAdmin(userUpdateDTO);
        return ResponseEntity.ok(updatedUserDTO); // Renvoie un statut 200 OK avec l'utilisateur mis à jour.
    }

    /**
     * Endpoint pour mettre à jour les informations d'un utilisateur par lui-même.
     * @param userUpdateDTO Un objet DTO contenant les données de mise à jour de l'utilisateur.
     * @return Un objet `UserResponseDTO` contenant les informations mises à jour de l'utilisateur.
     */
    @PutMapping("/update/self")
    public ResponseEntity<UserResponseDTO> updateUserBySelf(@RequestBody UserUpdateBySelfDTO userUpdateDTO) {
        UserResponseDTO updatedUserDTO = userService.updateUserBySelf(userUpdateDTO);
        return ResponseEntity.ok(updatedUserDTO); // Renvoie un statut 200 OK avec l'utilisateur mis à jour.
    }

    /**
     * Endpoint pour récupérer un utilisateur à partir de son ID.
     * @param id L'identifiant de l'utilisateur à rechercher.
     * @return Un objet `UserResponseDTO` contenant les informations de l'utilisateur trouvé.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO); // Renvoie un statut 200 OK avec les détails de l'utilisateur.
    }

    /**
     * Endpoint pour récupérer un utilisateur à partir de son surname.
     * @param surname Le nom de famille de l'utilisateur à rechercher.
     * @return Un objet `UserResponseDTO` contenant les informations de l'utilisateur trouvé.
     */
    @GetMapping("/find/surname/{surname}")
    public ResponseEntity<UserResponseDTO> getUserBySurname(@PathVariable String surname) {
        UserResponseDTO userResponseDTO = userService.getUserByName(surname);
        return ResponseEntity.ok(userResponseDTO); // Renvoie un statut 200 OK avec les détails de l'utilisateur.
    }

    /**
     * Endpoint pour récupérer un utilisateur à partir de son email.
     * @param email L'email de l'utilisateur à rechercher.
     * @return Un objet `UserResponseDTO` contenant les informations de l'utilisateur trouvé.
     */
    @GetMapping("/find/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO userResponseDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponseDTO); // Renvoie un statut 200 OK avec les détails de l'utilisateur.
    }

    /**
     * Endpoint pour supprimer un utilisateur à partir de son ID.
     * @param id L'identifiant de l'utilisateur à supprimer.
     * @return Une réponse HTTP 204 No Content pour indiquer la réussite de l'opération.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id); // Appelle le service pour supprimer l'utilisateur.
        return ResponseEntity.noContent().build(); // Renvoie un statut 204 No Content.
    }
    
   
}
