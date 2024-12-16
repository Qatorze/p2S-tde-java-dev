package com.qatorze.p2S_tde.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Représente un article dans le système.
 * Cette entité est mappée à la table "articles" dans la base de données.
 */
@Entity
@Table(name = "articles")
public class Article {

    /**
     * L'identifiant unique de l'article
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * Le titre de l'article
     */
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    /**
     * Le contenu de l'article
     */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * Les URL des images associées à l'article
     */
    @ElementCollection
    @CollectionTable(name = "article_images", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "image_url", length = 255, nullable = false)
    private List<String> imageUrls;

    /**
     * La date de création de l'article
     */
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    /**
     * L'auteur de l'article
     */
    @Column(name = "author", length = 50, nullable = false)
    private String author;

    /**
     * Constructeur sans paramètres
     */
    public Article() {}

    /**
     * Constructeur avec les champs principaux
     * 
     * @param title Le titre de l'article
     * @param content Le contenu de l'article
     * @param imageUrls Les URL des images associées
     * @param creationDate La date de création de l'article
     * @param author L'auteur de l'article
     */
    public Article(String title, String content, List<String> imageUrls, LocalDateTime creationDate, String author) {
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
        this.creationDate = creationDate;
        this.author = author;
    }

    /**
     * Constructeur avec tous les champs (y compris l'ID)
     * 
     * @param id L'identifiant unique de l'article
     * @param title Le titre de l'article
     * @param content Le contenu de l'article
     * @param imageUrls Les URL des images associées
     * @param creationDate La date de création de l'article
     * @param author L'auteur de l'article
     */
    public Article(Long id, String title, String content, List<String> imageUrls, LocalDateTime creationDate, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
        this.creationDate = creationDate;
        this.author = author;
    }

    /**
     * Retourne l'identifiant unique de l'article
     * 
     * @return L'identifiant de l'article
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'article
     * 
     * @param id L'identifiant de l'article
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le titre de l'article
     * 
     * @return Le titre de l'article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de l'article
     * 
     * @param title Le titre de l'article
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retourne le contenu de l'article
     * 
     * @return Le contenu de l'article
     */
    public String getContent() {
        return content;
    }

    /**
     * Définit le contenu de l'article
     * 
     * @param content Le contenu de l'article
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retourne les URL des images associées à l'article
     * 
     * @return Les URL des images
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * Définit les URL des images associées à l'article
     * 
     * @param imageUrls Les URL des images
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * Retourne la date de création de l'article
     * 
     * @return La date de création
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Définit la date de création de l'article
     * 
     * @param creationDate La date de création
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Retourne l'auteur de l'article
     * 
     * @return L'auteur de l'article
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Définit l'auteur de l'article
     * 
     * @param author L'auteur de l'article
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Représentation textuelle de l'article pour les journaux
     * 
     * @return Une chaîne contenant toutes les informations de l'article
     */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", content=");
		builder.append(content);
		builder.append(", imageUrls=");
		builder.append(imageUrls);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", author=");
		builder.append(author);
		builder.append("]");
		return builder.toString();
	}
    
}
