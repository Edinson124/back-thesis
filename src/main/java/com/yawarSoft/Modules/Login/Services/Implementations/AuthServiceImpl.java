package com.yawarSoft.Modules.Login.Services.Implementations;

import com.yawarSoft.Core.Entities.AuthEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Login.Repositories.AuthRepository;
import com.yawarSoft.Modules.Login.Services.Interfaces.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public String getUserFullName(String username) {
        AuthEntity auth = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        UserEntity user = auth.getUser();
        String firstName = Optional.ofNullable(user.getFirstName()).orElse("");
        String lastName = Optional.ofNullable(user.getLastName()).orElse("");
        String secondLastName = Optional.ofNullable(user.getSecondLastName()).orElse("");
        return String.format("%s %s %s", firstName, lastName, secondLastName).trim().replaceAll(" +", " ");
    }


}
