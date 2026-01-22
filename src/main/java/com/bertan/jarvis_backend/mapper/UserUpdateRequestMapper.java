package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.user.UserUpdateRequest;
import com.bertan.jarvis_backend.model.User;

public class UserUpdateRequestMapper {
    public static void updateEntity(User user, UserUpdateRequest dto) {
        if (dto == null || user == null) return;

        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.fullName() != null) {
            user.setFullName(dto.fullName());
        }
        if (dto.avatarUrl() != null) {
            user.setAvatarUrl(dto.avatarUrl());
        }
    }
}
