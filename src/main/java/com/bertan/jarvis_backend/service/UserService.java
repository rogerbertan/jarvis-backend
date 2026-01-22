package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.user.UserRequest;
import com.bertan.jarvis_backend.dto.user.UserResponse;
import com.bertan.jarvis_backend.dto.user.UserUpdateRequest;
import com.bertan.jarvis_backend.exception.ConflictException;
import com.bertan.jarvis_backend.exception.EntityNotFoundException;
import com.bertan.jarvis_backend.mapper.UserRequestMapper;
import com.bertan.jarvis_backend.mapper.UserResponseMapper;
import com.bertan.jarvis_backend.mapper.UserUpdateRequestMapper;
import com.bertan.jarvis_backend.model.User;
import com.bertan.jarvis_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return UserResponseMapper.toDTOList(userRepository.findAll());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return UserResponseMapper.toDTO(user);
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflictException("User with email " + request.email() + " already exists");
        }

        User user = UserRequestMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return UserResponseMapper.toDTO(savedUser);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.findByEmail(request.email()).isPresent()) {
                throw new ConflictException("User with email " + request.email() + " already exists");
            }
        }

        UserUpdateRequestMapper.updateEntity(user, request);
        User updatedUser = userRepository.save(user);
        return UserResponseMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
