package com.qatorze.p2S_tde.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qatorze.p2S_tde.dtos.UserRequestDTO;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

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
     * Endpoint pour mettre à jour un utilisateur existant.
     * @param userRequestDTO Un objet DTO contenant les données de mise à jour de l'utilisateur.
     * @return Un objet `UserResponseDTO` contenant les données mises à jour de l'utilisateur.
     */
    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUserDTO = userService.updateUser(userRequestDTO);
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
