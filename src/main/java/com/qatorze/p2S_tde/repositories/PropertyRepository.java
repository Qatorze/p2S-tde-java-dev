package com.qatorze.p2S_tde.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qatorze.p2S_tde.enums.PropertyCategory;
import com.qatorze.p2S_tde.enums.PropertyType;
import com.qatorze.p2S_tde.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Trouve toutes les propriétés ayant un type spécifique.
     *
     * @param type Type de propriété recherché
     * @return Liste des propriétés correspondant à ce type
     */
    List<Property> findByType(PropertyType type);

    /**
     * Trouve toutes les propriétés appartenant à une catégorie spécifique.
     *
     * @param category Catégorie de la propriété recherchée
     * @return Liste des propriétés correspondant à cette catégorie
     */
    List<Property> findByCategory(PropertyCategory category);

    /**
     * Trouve toutes les propriétés ayant un type parmi une liste donnée.
     *
     * @param types Liste des types de propriétés recherchées
     * @return Liste des propriétés correspondant à un type parmi la liste
     */
    List<Property> findByTypeIn(List<PropertyType> types);

    /**
     * Trouve toutes les propriétés ayant un type parmi une liste donnée et une catégorie spécifique.
     *
     * @param types   Liste des types de propriétés recherchées
     * @param category Catégorie de la propriété recherchée
     * @return Liste des propriétés correspondant à cette catégorie et à un type parmi la liste
     */
    List<Property> findByTypeInAndCategory(List<PropertyType> types, PropertyCategory category);

    /**
     * Trouve toutes les propriétés avec une localisation contenant une ville ou un pays spécifique.
     *
     * @param locationPart Partie de la localisation recherchée (ville, pays, etc.)
     * @return Liste des propriétés ayant une localisation contenant cette partie de localisation
     */
    List<Property> findByLocationContainingIgnoreCase(String locationPart);

    /**
     * Trouve toutes les propriétés ayant un type, une catégorie et une localisation spécifiques.
     *
     * @param type     Type de la propriété recherchée
     * @param category Catégorie de la propriété recherchée
     * @param location Partie de la localisation recherchée
     * @return Liste des propriétés correspondant à ces critères (type, catégorie, localisation)
     */
    List<Property> findByTypeAndCategoryAndLocationContainingIgnoreCase(
        PropertyType type,
        PropertyCategory category,
        String location
    );

    /**
     * Trouve toutes les propriétés ayant une liste de types, une catégorie et une localisation.
     *
     * @param types    Liste des types de propriétés recherchées
     * @param category Catégorie de la propriété recherchée
     * @param location Partie de la localisation recherchée
     * @return Liste des propriétés correspondant à ces critères (types, catégorie, localisation)
     */
    List<Property> findByTypeInAndCategoryAndLocationContainingIgnoreCase(
        List<PropertyType> types,
        PropertyCategory category,
        String location
    );

    /**
     * Trouve toutes les propriétés avec une catégorie spécifique et une localisation contenant une ville ou un pays.
     *
     * @param category  Catégorie de la propriété recherchée
     * @param location Partie de la localisation recherchée (ville, pays, etc.)
     * @return Liste des propriétés correspondant à cette catégorie et contenant cette localisation
     */
    List<Property> findByCategoryAndLocationContainingIgnoreCase(PropertyCategory category, String location);
    
    /**
     * Compte le nombre total de propriétés dans la base de données.
     * @return le nombre total de propriétés.
     */
    long count();
}
