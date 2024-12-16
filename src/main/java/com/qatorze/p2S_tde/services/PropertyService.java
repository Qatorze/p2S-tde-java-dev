package com.qatorze.p2S_tde.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qatorze.p2S_tde.enums.PropertyCategory;
import com.qatorze.p2S_tde.enums.PropertyType;
import com.qatorze.p2S_tde.exceptions.PropertiesNotFoundException;
import com.qatorze.p2S_tde.models.Property;
import com.qatorze.p2S_tde.repositories.PropertyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * Crée une nouvelle propriété.
     * 
     * @param property L'entité Property à créer.
     * @return La propriété créée.
     */
    public Property createProperty(Property property) {
    	if (property.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return propertyRepository.save(property);
    }

    /**
     * Met à jour une propriété existante.
     * 
     * @param property L'entité Property avec les données mises à jour.
     * @return La propriété mise à jour.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'existe pas.
     */
    public Property updateProperty(Property property) {
        // Vérifie si la propriété existe avant de la mettre à jour
        if (property.getId() == null || !propertyRepository.existsById(property.getId())) {
            throw new IllegalArgumentException("La propriété avec l'ID fourni n'existe pas.");
        }
        return propertyRepository.save(property);
    }

    /**
     * Récupère une propriété par son ID.
     * 
     * @param id L'ID de la propriété à récupérer.
     * @return La propriété correspondante.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'est pas trouvée.
     */
    public Property getPropertyById(Long id) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty()) {
            throw new IllegalArgumentException("Propriété non trouvée pour l'ID: " + id);
        }
        return optionalProperty.get();
    }

    /**
     * Supprime une propriété par son ID.
     * 
     * @param id L'ID de la propriété à supprimer.
     * @throws IllegalArgumentException Si la propriété avec l'ID fourni n'existe pas.
     */
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new IllegalArgumentException("La propriété avec l'ID fourni n'existe pas.");
        }
        propertyRepository.deleteById(id);
    }

    /**
     * Récupère les propriétés en fonction de plusieurs critères : types, catégorie et localisation.
     * 
     * @param types Liste des types de propriétés (peut être vide ou null).
     * @param category La catégorie de la propriété (non null).
     * @param location Partie de la localisation (ville ou pays).
     * @return Liste de propriétés qui correspondent aux critères de recherche.
     * @throws PropertiesNotFoundException Si aucune propriété n'est trouvée pour les critères donnés.
     */
    public List<Property> getPropertiesByFilter(List<PropertyType> types, PropertyCategory category, String location) {
        List<Property> properties;

        if ((types == null || types.isEmpty()) && location != null && !location.isEmpty()) {
            properties = propertyRepository.findByCategoryAndLocationContainingIgnoreCase(category, location);
        } else if (types != null && !types.isEmpty() && location != null && !location.isEmpty()) {
            properties = propertyRepository.findByTypeInAndCategoryAndLocationContainingIgnoreCase(types, category, location);
        } else if ((types == null || types.isEmpty()) && (location == null || location.isEmpty())) {
            properties = propertyRepository.findByCategory(category);
        } else if (types != null && !types.isEmpty() && (location == null || location.isEmpty())) {
            properties = propertyRepository.findByTypeInAndCategory(types, category);
        } else {
            properties = propertyRepository.findByCategory(category);
        }

        if (properties.isEmpty()) {
            throw new PropertiesNotFoundException("Aucune propriété trouvée pour les critères spécifiés.");
        }

        return properties;
    }
}
