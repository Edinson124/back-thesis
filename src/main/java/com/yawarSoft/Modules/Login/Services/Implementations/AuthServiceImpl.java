package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Core.Entities.AuthEntity;
import com.yawarSoft.Repositories.AuthRepository;
import com.yawarSoft.Services.Interfaces.AuthService;
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
