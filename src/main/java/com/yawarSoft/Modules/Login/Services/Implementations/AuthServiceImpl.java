package com.yawarSoft.Modules.Login.Services.Implementations;

import com.yawarSoft.Core.Entities.AuthEntity;
import com.yawarSoft.Modules.Login.Repositories.AuthRepository;
import com.yawarSoft.Modules.Login.Services.Interfaces.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void saveAuth(AuthEntity authEntity) {
        authRepository.save(authEntity);
    }
}
