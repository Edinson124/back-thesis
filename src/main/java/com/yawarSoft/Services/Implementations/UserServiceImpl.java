package com.yawarSoft.Services.Implementations;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.RoleEntity;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Mappers.UserMapper;
import com.yawarSoft.Repositories.RoleRepository;
import com.yawarSoft.Repositories.UserRepository;
import com.yawarSoft.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDTO> getUsersPaginated(int page, int size, String search, String role, String status) {
        Pageable pageable = PageRequest.of(page, size);

        // Si los parámetros son vacíos (""), los convertimos en null
        search = (search != null && !search.isBlank()) ? search : null;
        role = (role != null && !role.isBlank()) ? role : null;
        status = (status != null && !status.isBlank()) ? status : null;

        return userRepository.findByFilters(search, role, status, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setDocumentType(userDetails.getDocumentType());
            user.setDocumentNumber(userDetails.getDocumentNumber());
            user.setFirstNames(userDetails.getFirstNames());
            user.setLastName(userDetails.getLastName());
            user.setSecondLastName(userDetails.getSecondLastName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setStatus(userDetails.getStatus());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserEntity createUser(UserEntity user, Set<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        if (roles.isEmpty()) {
            throw new RuntimeException("Error: No se encontraron roles válidos.");
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setStatus("INACTIVO"); // Cambiar estado
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }
    }
}