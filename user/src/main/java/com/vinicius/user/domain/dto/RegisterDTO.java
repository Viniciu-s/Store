package com.vinicius.user.domain.dto;

import com.vinicius.user.domain.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}