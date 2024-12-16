package com.qatorze.p2S_tde.restcontroller;

import com.qatorze.p2S_tde.models.Property;
import com.qatorze.p2S_tde.services.PropertyService;
import com.qatorze.p2S_tde.enums.PropertyCategory;
import com.qatorze.p2S_tde.enums.PropertyType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Property", description = "Endpoint pour la gestion des propriétés")
@RestController
@RequestMapping("/api/properties")
public class PropertyRestController {

    @Autowired
    private PropertyService propertyService;

    /**
     * Endpoint pour créer une nouvelle propriété.
     * 
     * @param property L'objet Property contenant les informations nécessaires pour créer la propriété.
     * @return La propriété créée avec ses informations.
     */
    @PostMapping("/create")
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        // Crée une nouvelle propriété à partir des données reçues dans la requête.
        Property createdProperty = propertyService.createProperty(property);
        // Retourne la propriété créée dans la réponse.
        return ResponseEntity.ok(createdProperty);
    }

    /**
     * Endpoint pour mettre à jour une propriété existante.
     * 
     * @param property L'objet Property contenant les informations mises à jour de la propriété.
     * @return La propriété mise à jour avec ses nouvelles informations.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'existe pas.
     */
    @PutMapping("/update")
    public ResponseEntity<Property> updateProperty(@RequestBody Property property) {
        // Met à jour la propriété en fonction des données reçues.
        Property updatedProperty = propertyService.updateProperty(property);
        // Retourne la propriété mise à jour dans la réponse.
        return ResponseEntity.ok(updatedProperty);
    }

    /**
     * Endpoint pour récupérer une propriété par son ID.
     * 
     * @param id L'ID de la propriété à récupérer.
     * @return La propriété correspondante à l'ID.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'est pas trouvée.
     */
    @GetMapping("find/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        // Récupère la propriété par son ID.
        Property property = propertyService.getPropertyById(id);
        // Retourne la propriété trouvée dans la réponse.
        return ResponseEntity.ok(property);
    }

    /**
     * Endpoint pour supprimer une propriété par son ID.
     * 
     * @param id L'ID de la propriété à supprimer.
     * @return La réponse indiquant si la suppression a été effectuée avec succès.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'existe pas.
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        // Supprime la propriété en fonction de son ID.
        propertyService.deleteProperty(id);
        return ResponseEntity.ok("Propriété supprimée avec succès.");
    }

    /**
     * Récupère les propriétés en fonction de plusieurs critères : types, catégorie et localisation.
     * 
     * @param types Liste des types de propriétés (peut être vide ou null).
     * @param category La catégorie de la propriété (non null).
     * @param location Partie de la localisation (ville ou pays).
     * @return Liste de propriétés qui correspondent aux critères de recherche.
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Property>> getPropertiesByFilter(
            @RequestParam(required = false) List<PropertyType> types,
            @RequestParam PropertyCategory category,
            @RequestParam(required = false) String location) {
        // Appelle le service pour récupérer les propriétés filtrées.
        List<Property> properties = propertyService.getPropertiesByFilter(types, category, location);
        return ResponseEntity.ok(properties); // Retourne la liste des propriétés.
    }
}
