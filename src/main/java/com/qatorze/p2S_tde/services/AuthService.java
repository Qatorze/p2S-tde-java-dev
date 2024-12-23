package com.qatorze.p2S_tde.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qatorze.p2S_tde.dtos.RegisterRequestDTO;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.exceptions.UserEmailAlreadyInUseException;
import com.qatorze.p2S_tde.exceptions.InvalidCredentialsException;
import com.qatorze.p2S_tde.mapper.UserConverter;
import com.qatorze.p2S_tde.models.User;
import com.qatorze.p2S_tde.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service // Annotation pour déclarer cette classe comme un service.
public class AuthService {
	
	@Autowired
    private UserRepository userRepository; // Injection du repository pour interagir avec la base de données.
    
    @Autowired
    private UserConverter userConverter; // Conversion des objets entre les DTO et les modèles.

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Encodage des mots de passe pour la sécurité.
    
    
    @Autowired
    private PasswordValidatorService passwordValidatorService; // Iniezione del servizio di validazione

	
    /**
     * Authentifie un utilisateur à partir de son email et mot de passe.
     */
	public UserResponseDTO login(String loginEmail, String loginPassword) {
		
		Optional<User> optUser = userRepository.findByEmail(loginEmail);
		// Si aucun utilisateur n'est trouvé, lance une exception.
		if (optUser.isEmpty()) {
			throw new InvalidCredentialsException(); 
		}
		User user = optUser.get();
		
		// Vérifie si le mot de passe fourni correspond au mot de passe encodé en base.
	    if (!passwordEncoder.matches(loginPassword, user.getPassword())) {
	        throw new InvalidCredentialsException();
	    } 
	    
	    // Convertit l'utilisateur en DTO pour répondre au front-end.
	    UserResponseDTO userResponse = userConverter.convertUserToUserResponseDTO(user);
	    
	  
	   
	    return userResponse;
	}
	
	/**
     * Enregistre un nouvel utilisateur.
     */
	@Transactional
	public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
		// Vérifie si l'email est déjà utilisé.
		Optional<User> optUser = userRepository.findByEmail(registerRequestDTO.getEmail());
        if (optUser.isPresent()) {
            throw new UserEmailAlreadyInUseException();
        }  
        
        // Convertit le DTO en modèle User pour le stocker en base.
        User newUser = userConverter.convertRegisterRequestDTO_ToUser(registerRequestDTO);
        
        // Encode le mot de passe avant de le sauvegarder.
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole("user"); // Définit un rôle par défaut "user" car à ce stade on a le Model User et non plus le RegisterRequestDTO

        // Sauvegarde le nouvel utilisateur dans la base de données.
        User savedUser = userRepository.save(newUser);
       
        // Convertit l'utilisateur sauvegardé en DTO pour la réponse.
        UserResponseDTO userResponse = userConverter.convertUserToUserResponseDTO(savedUser);
        
       
	   
	    return userResponse;
    }
	
	public void changePassword(String email, String oldPassword, String newPassword) {
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("L'email inserée n'est pas associée à ce compte."));

	    // Verifica che la vecchia password sia corretta
	    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	        throw new RuntimeException("Le mot de passe courant est incorrecte.");
	    }

	    passwordValidatorService.validateNewPassword(newPassword, user);

	    String hashedNewPassword = passwordEncoder.encode(newPassword);
	    user.addPasswordToHistory(hashedNewPassword);
	    // Aggiorna la password con la nuova se la nuova password non fa parte delle ultime 5
	    user.setPassword(hashedNewPassword);
	    userRepository.save(user);
	}
	
}

