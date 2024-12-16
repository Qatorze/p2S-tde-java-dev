package com.qatorze.p2S_tde.models;

import com.qatorze.p2S_tde.enums.PropertyCategory;
import com.qatorze.p2S_tde.enums.PropertyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe représentant une propriété immobilière.
 * Contient les informations de la propriété telles que le titre, la description, 
 * le type, la catégorie, la localisation (ville, quartier, pays), le prix, la superficie,
 * la date d'enregistrement, ainsi que les images associées.
 * 
 * Cette classe sera mappée à une table dans la base de données.
 */
@Entity
@Table(name = "properties") // Nom de la table dans la base de données
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    @Column(name = "id") // Nom de la colonne dans la table de base de données
    private Long id; // Identifiant unique de la propriété, clé primaire de la table

    @Size(min = 5, max = 100)
    @Column(name = "title", nullable = false) // Colonne 'title' dans la table
    private String title; // Titre de la propriété

    @NotNull
    @Size(max = 500)
    @Column(name = "description", nullable = false) // Colonne 'description' dans la table
    private String description; // Description détaillée de la propriété

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false) // Colonne 'type' dans la table
    private PropertyType type; // Type de la propriété (appartement, maison, etc.)

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false) // Colonne 'category' dans la table
    private PropertyCategory category; // Catégorie de la propriété (vente, location, etc.)

    @Positive
    @Column(name = "price", nullable = false) // Colonne 'price' dans la table
    private Double price; // Prix de la propriété

    @Column(name = "location", nullable = true) // Colonne 'location' dans la table
    private String location; // Localisation complète sous la forme "ville, quartier, pays"

    @Column(name = "city", nullable = true) // Colonne 'city' dans la table
    private String city; // Ville de la propriété, utilisé pour la recherche spécifique

    @Column(name = "neighborhood", nullable = true) // Colonne 'neighborhood' dans la table
    private String neighborhood; // Quartier de la propriété, utilisé pour la recherche spécifique

    @Column(name = "country", nullable = true) // Colonne 'country' dans la table
    private String country; // Pays de la propriété, utilisé pour la recherche spécifique

    @Column(name = "area", nullable = false) // Colonne 'area' dans la table
    private Double area; // Superficie de la propriété (en mètres carrés)

    @Column(name = "rooms", nullable = false) // Colonne 'rooms' dans la table
    private Integer rooms; // Nombre de chambres dans la propriété

    @Column(name = "registration_date", nullable = false) // Colonne 'registration_date' dans la table
    private LocalDateTime registrationDate; // Date d'enregistrement de la propriété

    
    /**
     * Liste des URLs des images associées à la propriété.
     * 
     * Cet attribut est mappé à une table dédiée dans la base de données, appelée "property_images".
     * Chaque URL d'image est stockée dans une ligne distincte de cette table, avec une colonne 
     * associée à l'identifiant unique de la propriété.
     *
     * Les annotations utilisées :
     * 
     * - {@code @ElementCollection} : Cette annotation indique que l'attribut est une collection
     *   d'éléments élémentaires (ici, des chaînes de caractères représentant des URLs) qui seront 
     *   mappés dans une table distincte liée à l'entité principale.
     * 
     * - {@code @CollectionTable} : Définit la table qui contiendra les éléments de la collection. 
     *   - L'attribut {@code name} spécifie le nom de cette table, ici "property_images".
     *   - L'attribut {@code joinColumns} établit une relation avec la table principale 
     *     (ici, "properties"), en utilisant une clé étrangère définie par l'annotation {@code @JoinColumn}.
     *     La colonne "property_id" dans la table "property_images" sert de clé étrangère pointant vers
     *     l'identifiant unique de la propriété dans la table "properties".
     * 
     * - {@code @Column} : Spécifie le nom de la colonne qui stockera les valeurs de la collection
     *   (dans ce cas, les URLs d'images). Ici, la colonne est nommée "image_url".
     * 
     * Fonctionnement :
     * 
     * Lorsque vous ajoutez ou modifiez des URLs dans la liste {@code imageUrls}, Hibernate
     * gère automatiquement les insertions, suppressions ou mises à jour des lignes dans la 
     * table "property_images". Cela permet de maintenir une relation propre et normalisée entre 
     * la table principale et les données associées.
     * 
     * Exemple de structure de la table "property_images" :
     * 
     * | property_id | image_url               |
     * |-------------|-------------------------|
     * | 1           | https://example.com/1  |
     * | 1           | https://example.com/2  |
     * | 2           | https://example.com/3  |
     * 
     * Cela permet à une propriété (identifiée par "property_id") d'avoir plusieurs images associées.
     */
    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url") // Colonne pour stocker les URLs des images
    private List<String> imageUrls; // Liste des URLs des images associées à la propriété

    // Constructeur par défaut
    public Property() {}

    // Constructeur pour initialiser une nouvelle propriété
    public Property(@Size(min = 5, max = 100) String title, @NotNull @Size(max = 500) String description,
                    PropertyType type, PropertyCategory category, @Positive Double price, String location,
                    String city, String neighborhood, String country, Double area, Integer rooms,
                    LocalDateTime registrationDate, List<String> imageUrls) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.price = price;
        this.location = location;
        this.city = city;
        this.neighborhood = neighborhood;
        this.country = country;
        this.area = area;
        this.rooms = rooms;
        this.registrationDate = registrationDate;
        this.imageUrls = imageUrls;
    }

    // Getters et setters pour chaque attribut

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public PropertyCategory getCategory() {
        return category;
    }

    public void setCategory(PropertyCategory category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Property [id=");
        builder.append(id);
        builder.append(", title=");
        builder.append(title);
        builder.append(", description=");
        builder.append(description);
        builder.append(", type=");
        builder.append(type);
        builder.append(", category=");
        builder.append(category);
        builder.append(", price=");
        builder.append(price);
        builder.append(", location=");
        builder.append(location);
        builder.append(", city=");
        builder.append(city);
        builder.append(", neighborhood=");
        builder.append(neighborhood);
        builder.append(", country=");
        builder.append(country);
        builder.append(", area=");
        builder.append(area);
        builder.append(", rooms=");
        builder.append(rooms);
        builder.append(", registrationDate=");
        builder.append(registrationDate);
        builder.append(", imageUrls=");
        builder.append(imageUrls);
        builder.append("]");
        return builder.toString();
    }
}
