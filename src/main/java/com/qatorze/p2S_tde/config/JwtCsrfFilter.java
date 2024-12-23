package com.qatorze.p2S_tde.config;

import java.io.IOException; 



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCsrfFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        String csrfToken = request.getHeader("X-CSRF-Token");

        try {
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7); // Rimuove "Bearer " dall'inizio

                // Validazione del token JWT e ottenimento dei dati utente
                UserResponseDTO user = jwtService.validateTokenAndGetUser(jwtToken);

                // Validazione del token CSRF
                if (csrfToken != null && jwtService.validateCsrfToken(csrfToken)) {
                    // Propaga i dettagli dell'utente al contesto di sicurezza
                    request.setAttribute("user", user);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token is invalid or missing.");
                    return;
                }
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
            return;
        }

        // Procedi con la catena dei filtri
        filterChain.doFilter(request, response);
    }
}
