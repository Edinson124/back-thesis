package com.yawarSoft.Services.Implementations;
import com.yawarSoft.Dto.ApiResponse;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.RoleEntity;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Enums.UserStatus;
import com.yawarSoft.Mappers.UserMapper;
import com.yawarSoft.Repositories.RoleRepository;
import com.yawarSoft.Repositories.UserRepository;
import com.yawarSoft.Services.Interfaces.ImageStorageService;
import com.yawarSoft.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final ImageStorageService imageStorageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper, ImageStorageService imageStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public Page<UserDTO> getUsersPaginated(int page, int size, String search, String role, String status) {
        Pageable pageable = PageRequest.of(page, size);

        // Si los parámetros son vacíos (""), los convertimos en null
        search = (search != null && !search.isBlank()) ? search : null;
        role = (role != null && !role.isBlank()) ? role : null;
        UserStatus userStatus = (status != null && !status.isBlank()) ? UserStatus.valueOf(status) : null;
        return userRepository.findByFilters(search, role, userStatus, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserEntity updateUser(Integer id, UserEntity userDetails) {
        if (userRepository.existsByDocumentNumberAndIdNot(userDetails.getDocumentNumber(), id)) {
            throw new RuntimeException("El número de documento ya está registrado con otro usuario.");
        }
        return userRepository.findById(id).map(user -> {
            user.setDocumentType(userDetails.getDocumentType());
            user.setDocumentNumber(userDetails.getDocumentNumber());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setSecondLastName(userDetails.getSecondLastName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setStatus(userDetails.getStatus());
            user.setBirthDate(userDetails.getBirthDate());
            user.setDistrict(userDetails.getDistrict());
            user.setRegion(userDetails.getRegion());
            user.setProvince(userDetails.getProvince());
            user.setGender(userDetails.getGender());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserEntity createUser(UserEntity user, Set<Integer> roleIds) {
        Set<RoleEntity> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        if (roles.isEmpty()) {
            throw new RuntimeException("Error: No se encontraron roles válidos.");
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UserDTO changeStatus(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setStatus(user.getStatus() == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE);
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.toDto(updatedUser);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + userId);
        }
    }

    @Override
    public String updateUserProfileImage(Integer userId, MultipartFile profileImage) throws IOException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Guardar la imagen y obtener la URL
            String imageUrl = imageStorageService.storeImage(userId,profileImage);
            user.setProfileImageUrl(imageUrl);

            // Guardar el usuario actualizado
            userRepository.save(user);
            return imageUrl;
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteUserProfileImage(Integer userId) {
        // Buscar al usuario en la base de datos
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND,"Usuario no encontrado."));
        }

        UserEntity user = optionalUser.get();

        // Verificar si el usuario tiene una imagen asignada
        if (user.getProfileImageUrl() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND,"El usuario no tiene una imagen de perfil."));
        }

        // Eliminar la imagen del servidor
        boolean deleted = imageStorageService.deleteImage(user.getProfileImageUrl());
        if (!deleted) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Error al eliminar la imagen del servidor."));
        }

        // Actualizar la URL de la imagen en la base de datos (poner en null)
        user.setProfileImageUrl(null);
        userRepository.save(user);

        return ResponseEntity
                .ok(new ApiResponse(HttpStatus.OK,"Imagen eliminada y perfil actualizado correctamente."));
    }
}