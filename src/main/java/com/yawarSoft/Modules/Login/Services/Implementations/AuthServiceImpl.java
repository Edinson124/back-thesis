package com.yawarSoft.Modules.Login.Services.Implementations;

import com.yawarSoft.Core.Entities.*;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Modules.Login.Dto.UserRoleDTO;
import com.yawarSoft.Modules.Login.Repositories.AuthRepository;
import com.yawarSoft.Modules.Login.Services.Interfaces.AuthService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public AuthServiceImpl(AuthRepository authRepository, AuthenticatedUserService authenticatedUserService) {
        this.authRepository = authRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public void saveAuth(AuthEntity authEntity) {
        authRepository.save(authEntity);
    }

    @Override
    public List<String> getUserFullNameAndRoleName(String username) {
        AuthEntity auth = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        UserEntity user = auth.getUser();

        String firstName = Optional.ofNullable(user.getFirstName()).orElse("");
        String lastName = Optional.ofNullable(user.getLastName()).orElse("");
        String secondLastName = Optional.ofNullable(user.getSecondLastName()).orElse("");

        String fullName = String.format("%s %s %s", firstName, lastName, secondLastName)
                .trim()
                .replaceAll(" +", " ");

        String roleName = Optional.ofNullable(user.getRole())
                .map(RoleEntity::getName)
                .orElse("SIN ROL");

        return List.of(fullName, roleName);
    }

    @Override
    public UserRoleDTO meRoleAndPermission() {
        UserEntity user = authenticatedUserService.getCurrentUser();
        RoleEntity role = user.getRole();
        Set<PermissionEntity> permissions = role.getPermissionList();

        String bloodBankName = null;
        String bloodBankType;

        List<String> filteredPermissionNames;

        // Si el usuario tiene banco de sangre, capturar nombre y tipo
        if (user.getBloodBank() != null) {
            BloodBankEntity bloodBank = user.getBloodBank();
            bloodBankName = bloodBank.getName();
            bloodBankType = bloodBank.getBloodBankType().getName();

            filteredPermissionNames = permissions.stream()
                    .filter(p ->
                            Boolean.TRUE.equals(p.getAllBloodBankType()) ||
                                    (bloodBankType != null && (bloodBankType.equalsIgnoreCase("II") || bloodBankType.equalsIgnoreCase("III")))
                    )
                    .map(PermissionEntity::getName)
                    .collect(Collectors.toList());
        } else {
            bloodBankType = null;
            // Admin u otro usuario sin banco asignado
            filteredPermissionNames = permissions.stream()
                    .map(PermissionEntity::getName)
                    .collect(Collectors.toList());
        }

        return UserRoleDTO.builder()
                .role(role.getName())
                .bloodBankName(bloodBankName)
                .bloodBankType(bloodBankType)
                .permissions(filteredPermissionNames)
                .build();
    }

}
