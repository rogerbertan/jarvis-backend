package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.user.UserResponse;
import com.bertan.jarvis_backend.model.User;

import java.util.List;

public class UserResponseMapper {
    public static UserResponse toDTO(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getAvatarUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static List<UserResponse> toDTOList(List<User> users) {
        return users.stream()
                .map(UserResponseMapper::toDTO)
                .toList();
    }
}
