package com.qatorze.p2S_tde.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qatorze.p2S_tde.dtos.UserRequestDTO;
import com.qatorze.p2S_tde.dtos.UserResponseDTO;
import com.qatorze.p2S_tde.exceptions.UserByIdNotFoundException;
import com.qatorze.p2S_tde.mapper.UserConverter;
import com.qatorze.p2S_tde.models.User;
import com.qatorze.p2S_tde.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Methode pour update les details du User
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        Optional<User> optUser = userRepository.findById(userRequestDTO.getId());
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userRequestDTO.getId());
        }
        
        User user = optUser.get();

        // Update user fields
        if (userRequestDTO.getSurname() != null) {
            user.setSurname(userRequestDTO.getSurname());
        }
        if (userRequestDTO.getName() != null) {
            user.setName(userRequestDTO.getName());
        }
        if (userRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword())); // Encrypt new password
        }
        if (userRequestDTO.getImagePath() != null) {
            user.setImagePath(userRequestDTO.getImagePath());
        }

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Convertit le User mis à jour en UserResponseDTO qui sera renvoyé au client.
        return userConverter.convertUserToUserResponseDTO(updatedUser);
    }

    // Methode pour récupérer les details du User à partir de son ID
    public UserResponseDTO getUserById(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userId);
        }
        return userConverter.convertUserToUserResponseDTO(optUser.get());
    }

    // Methode pour suprimer un User à partir de son ID
    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserByIdNotFoundException(userId);
        }
        userRepository.delete(optUser.get());
    }
}
