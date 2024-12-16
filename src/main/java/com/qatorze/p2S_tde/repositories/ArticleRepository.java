package com.qatorze.p2S_tde.repositories;

import com.qatorze.p2S_tde.models.Article;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface pour gérer les opérations CRUD sur les articles.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Rechercher un article par son titre.
     * 
     * @param title Le titre de l'article
     * @return Un article correspondant au titre donné
     */
    Optional<Article> findByTitleIgnoreCase(String title);
    
    /**
     * Rechercher un article par son auteur.
     * 
     * @param auteur L'auteur de l'article
     * @return Un article correspondant à l'auteur.
     */
    Optional<Article> findByAuthorIgnoreCase(String author);
    
    /**
     * Compte le nombre total d'articles dans la base de données.
     * @return le nombre total d'articles.
     */
    long count();
    
}
