package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.user.UserRequest;
import com.bertan.jarvis_backend.model.User;

public class UserRequestMapper {
    public static User toEntity(UserRequest dto) {
        if (dto == null) return null;

        User user = new User();
        user.setEmail(dto.email());
        user.setFullName(dto.fullName());
        user.setAvatarUrl(dto.avatarUrl());

        return user;
    }
}
