package com.qatorze.p2S_tde.services;

import com.qatorze.p2S_tde.models.Article;
import com.qatorze.p2S_tde.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer la logique métier des articles.
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Récupérer tous les articles.
     * 
     * @return La liste des articles
     */
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Récupérer un article par son ID.
     * 
     * @param id L'identifiant de l'article
     * @return L'article correspondant ou une exception si non trouvé
     * @throws IllegalArgumentException Si l'article n'est pas trouvé
     */
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article avec l'ID " + id + " introuvable."));
    }

    /**
     * Ajouter un nouvel article.
     * 
     * @param article L'article à ajouter
     * @return L'article ajouté
     */
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    /**
     * Mettre à jour un article existant.
     * 
     * @param id L'identifiant de l'article à mettre à jour
     * @param updatedArticle Les nouvelles données de l'article
     * @return L'article mis à jour
     * @throws IllegalArgumentException Si l'article à mettre à jour n'est pas trouvé
     */
    public Article updateArticle(Long id, Article updatedArticle) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article avec l'ID " + id + " introuvable."));

        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setContent(updatedArticle.getContent());
        existingArticle.setImageUrls(updatedArticle.getImageUrls());
        existingArticle.setAuthor(updatedArticle.getAuthor());

        return articleRepository.save(existingArticle);
    }

    /**
     * Supprimer un article par son ID.
     * 
     * @param id L'identifiant de l'article à supprimer
     * @throws IllegalArgumentException Si l'article à supprimer n'est pas trouvé
     */
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new IllegalArgumentException("Article avec l'ID " + id + " introuvable.");
        }
        articleRepository.deleteById(id);
    }

    /**
     * Rechercher un article par son titre.
     * 
     * @param title Le titre de l'article
     * @return Un Optional contenant l'article correspondant ou vide si non trouvé
     */
    public Optional<Article> getArticleByTitle(String title) {
        return articleRepository.findByTitleIgnoreCase(title);
    }
}
