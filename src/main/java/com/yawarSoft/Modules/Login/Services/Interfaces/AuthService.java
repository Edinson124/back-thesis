package com.yawarSoft.Modules.Login.Services.Interfaces;

import com.yawarSoft.Core.Entities.AuthEntity;

import java.util.List;

public interface AuthService {
    void saveAuth (AuthEntity authEntity);

    List<String> getUserFullNameAndRoleName(String username);
}
