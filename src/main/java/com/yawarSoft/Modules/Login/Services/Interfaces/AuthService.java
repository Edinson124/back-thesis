package com.yawarSoft.Modules.Login.Services.Interfaces;

import com.yawarSoft.Core.Entities.AuthEntity;

public interface AuthService {
    void saveAuth (AuthEntity authEntity);

    String getUserFullName(String username);
}
