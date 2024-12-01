package com.qatorze.p2S_tde.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // Déclare cette classe comme un composant Spring injectable.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	 /**
	 * Méthode appelée lorsqu'un utilisateur non authentifié tente d'accéder
	 * à une ressource nécessitant une authentification.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	        throws IOException {
		// Définit le statut HTTP comme 401 (Unauthorized).
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.setContentType("application/json");
	    
	    // Retourne une réponse JSON expliquant l'erreur.
	    response.getWriter().write("{\"error\": \"Unauthorized - " + authException.getMessage() + "\"}");
	}
}