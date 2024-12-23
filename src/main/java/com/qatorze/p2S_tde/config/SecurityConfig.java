package com.qatorze.p2S_tde.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.qatorze.p2S_tde.services.AuthService;
import com.qatorze.p2S_tde.services.JwtService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration // Indique à spring que cette classe contient des confugurations à gérer come "Bean". Spring va créer et gérer les objets définient dans cette classe
@EnableWebSecurity // Autorise les fonctionnalités de sécurité de Spring Security permettant la configuration de la protection pour les resource web.
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtService jwtService; 
    private final JwtCsrfFilter jwtCsrfFilter;
	private final AuthService authService; 

	// L'annotation @Lazy permet de différer l'initialisation de l'objet AuthService 
	// jusqu'à ce qu'il soit explicitement requis. Cela peut éviter des problèmes de dépendance circulaire
	// ou améliorer les performances au démarrage si le bean n'est pas immédiatement nécessaire.
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtService jwtService,@Lazy AuthService authService, JwtCsrfFilter jwtCsrfFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtService = jwtService;
		this.jwtCsrfFilter = jwtCsrfFilter;
        this.authService = authService; 
    }
    
    @Bean // Rend l'objet disponible dans le contest de l'application, permettant aux autres composants de l'injecter ou' necessaire, par exemple dans les service d'authentification
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de la sécurité HTTP et du filtrage des requêtes.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	
    	 // Creazione del filtro di autenticazione JWT personalizzato
         JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                 authenticationManager(authenticationConfiguration),
                 jwtService, // On passe le JwtService au filtre
                 authService // On passe le AuthService au filtre
         );
    	 
         // Configure le filtre pour gérer les erreurs de connexion.
    	 jwtAuthenticationFilter.setAuthenticationFailureHandler((request, response, exception) -> {
        	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             response.setContentType("application/json");
             response.getWriter().write("{\"error\": \"Login failed\"}");
         });
    	
    	// Configuration des autorisations et de la gestion des sessions.
        http
            .csrf(csrf -> csrf.disable()) // "Désactive CSRF pour les APIs stateless, car pas necessaire dans le cas des applications basées sur JWT car il n'existe pas de session coté server.
            .authorizeHttpRequests((requests) -> requests
            		 // Permetti l'accesso pubblico a tutti gli endpoint delle proprietà
                    .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()  // Permette l'accesso pubblico a tutti i GET per /api/properties/**
                    .requestMatchers(HttpMethod.POST, "/api/properties/**").permitAll() // Permette l'accesso pubblico a tutti i POST per /api/properties/**
                    .requestMatchers(HttpMethod.PUT, "/api/properties/**").permitAll()  // Permette l'accesso pubblico a tutti i PUT per /api/properties/**
                    .requestMatchers(HttpMethod.DELETE, "/api/properties/**").permitAll() // Permette l'accesso pubblico a tutti i DELETE per /api/properties/**

            		.requestMatchers("/api/auth/**").permitAll() // Endpoint public, comme login e register, accéssibles sans authentication, donc sans token.
            		.requestMatchers("/api/password-reset/request").permitAll()
            		.requestMatchers("/api/password-reset/reset").permitAll()
            		.requestMatchers("/api/password-reset/change").authenticated()
            		.requestMatchers("/password-change").authenticated()
            		.requestMatchers("/api/admin/**").hasRole("admin") // Questa riga specifica che solo gli utenti con il ruolo admin possono accedere agli endpoint che iniziano con /api/admin/.
            		.requestMatchers("/api/user/**").hasRole("user") // Questa riga specifica che solo gli utenti con il ruolo user possono accedere agli endpoint che iniziano con /api/user/.
            		.anyRequest().authenticated() //.permitAll() Tous les endpoints necessittent d'un token JWT valide pour l'access.
             )
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Définit un point d'entrée pour les erreurs
             )
            .sessionManagement(management -> management
            		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // On applique une gestion des sessions "stateless" (aucun session entre deux requètes lato server)
            )	
            .addFilterBefore(jwtCsrfFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre JWTCsrf avant le filtre predefinit.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ajoute le filtre JWT avant le filtre predefinit.

         return http.build(); // Retourne la configuration.
         }

    /**
     * Gestionnaire d'authentification Spring Security.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}