package com.yawarSoft.Core.Services.Implementations;

import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Utils.UserUtils;
import com.yawarSoft.Modules.Admin.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
    private final UserRepository userRepository;

    public AuthenticatedUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getCurrentUser() {
        UserEntity user = UserUtils.getAuthenticatedUser();
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
