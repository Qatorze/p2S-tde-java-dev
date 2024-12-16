package com.qatorze.p2S_tde.restcontroller;

import com.qatorze.p2S_tde.models.Article;
import com.qatorze.p2S_tde.services.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour gérer les articles.
 */
@Tag(name = "Articles", description = "Endpoints pour la gestion des articles")
@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    /**
     * Récupérer tous les articles.
     * 
     * @return Liste des articles
     */
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    /**
     * Récupérer un article par son ID.
     * 
     * @param id L'identifiant de l'article
     * @return L'article correspondant
     */
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    /**
     * Créer un nouvel article.
     * 
     * @param article Les données de l'article à créer
     * @return L'article créé
     */
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.createArticle(article));
    }

    /**
     * Mettre à jour un article existant.
     * 
     * @param id L'identifiant de l'article à mettre à jour
     * @param article Les nouvelles données de l'article
     * @return L'article mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return ResponseEntity.ok(articleService.updateArticle(id, article));
    }

    /**
     * Supprimer un article par son ID.
     * 
     * @param id L'identifiant de l'article à supprimer
     * @return Une réponse vide avec un statut HTTP approprié
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok("Artticle supprimée avec succès.");
    }

    /**
     * Rechercher un article par son titre.
     * 
     * @param title Le titre de l'article
     * @return L'article correspondant
     */
    @GetMapping("/search")
    public ResponseEntity<Optional<Article>> getArticleByTitle(@RequestParam String title) {
        return ResponseEntity.ok(articleService.getArticleByTitle(title));
    }
}
