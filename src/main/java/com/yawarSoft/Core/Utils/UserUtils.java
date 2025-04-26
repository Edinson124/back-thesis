package com.yawarSoft.Core.Utils;

import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Login.Models.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            return UserEntity.builder().id(userId).build();
        }
        return null; // Retornar null si no hay usuario autenticado
    }
}