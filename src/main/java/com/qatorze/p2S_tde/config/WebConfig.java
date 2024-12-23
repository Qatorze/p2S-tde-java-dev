package com.qatorze.p2S_tde.config;

import org.springframework.context.annotation.Configuration; 
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe di configurazione per gestire le impostazioni CORS (Cross-Origin Resource Sharing).
 * 
 * Questa classe estende WebMvcConfigurer e sovrascrive il metodo addCorsMappings per definire
 * le origini (origins) consentite per le richieste CORS, i metodi HTTP consentiti, le intestazioni,
 * e l'abilitazione delle credenziali (come i cookie).
 * 
 * Viene applicata a tutte le richieste che iniziano con "/api/" e consente l'accesso dal dominio
 * "http://localhost:4200" (comunemente utilizzato per lo sviluppo di applicazioni frontend con Angular).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Metodo per configurare le mappature CORS.
     * 
     * @param registry Oggetto CORS Registry che permette di configurare le impostazioni CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Applica la configurazione a tutte le richieste che iniziano con "/api/"
                .allowedOrigins("http://localhost:4200") //(8080 pour postMan) Consenti richieste solo dal dominio http://localhost:4200 (front-end Angular)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Consenti solo i metodi HTTP GET, POST, PUT e DELETE
                .allowedHeaders("*") // Consenti tutte le intestazioni (permette qualsiasi header nelle richieste)
                .allowCredentials(true); // Abilita l'invio e la ricezione dei cookie e altre credenziali (utile per l'autenticazione tramite cookie JWT)
    }
}
