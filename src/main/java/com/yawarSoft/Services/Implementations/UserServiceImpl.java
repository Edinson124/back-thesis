package com.yawarSoft.Services.Implementations;
import com.yawarSoft.Dto.ApiResponse;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Dto.UserListDTO;
import com.yawarSoft.Entities.BloodBankEntity;
import com.yawarSoft.Entities.RoleEntity;
import com.yawarSoft.Entities.UserEntity;
import com.yawarSoft.Enums.UserStatus;
import com.yawarSoft.Mappers.UserMapper;
import com.yawarSoft.Repositories.UserRepository;
import com.yawarSoft.Services.Interfaces.BloodBankService;
import com.yawarSoft.Services.Interfaces.ImageStorageService;
import com.yawarSoft.Services.Interfaces.RoleService;
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
    private final RoleService roleService;
    private final BloodBankService bloodBankService;
    private final UserMapper userMapper;
    private final ImageStorageService imageStorageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleService roleService, UserMapper userMapper,
                           BloodBankService bloodBankService,
                           ImageStorageService imageStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.bloodBankService = bloodBankService;
        this.userMapper = userMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public Page<UserListDTO> getUsersPaginated(int page, int size, String search, String role, String status) {
        Pageable pageable = PageRequest.of(page, size);

        search = (search != null && !search.isBlank()) ? search : null;
        role = (role != null && !role.isBlank()) ? role : null;
        UserStatus userStatus = (status != null && !status.isBlank()) ? UserStatus.valueOf(status) : null;
        return userRepository.findByFilters(search, role, userStatus, pageable)
                .map(userMapper::toListDto);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        return userMapper.toUserDto(userEntity);
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        if (!existingUser.getDocumentNumber().equals(userDto.getDocumentNumber()) &&
                userRepository.existsByDocumentNumber(userDto.getDocumentNumber())) {
            throw new IllegalArgumentException("El número de documento ya está registrado con otro usuario.");
        }

        if (userDto.getBloodBankId() == null) {
            existingUser.setBloodBank(null);
        } else {
            BloodBankEntity bloodBank = bloodBankService.getBloodBankEntityById(userDto.getBloodBankId())
                    .orElseThrow(() -> new IllegalArgumentException("Banco de sangre no encontrado."));
            existingUser.setBloodBank(bloodBank);
        }

        verifyRoles(userDto, existingUser);

        existingUser.setDocumentType(userDto.getDocumentType());
        existingUser.setDocumentNumber(userDto.getDocumentNumber());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setSecondLastName(userDto.getSecondLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setBirthDate(userDto.getBirthDate());
        existingUser.setDistrict(userDto.getDistrict());
        existingUser.setRegion(userDto.getRegion());
        existingUser.setProvince(userDto.getProvince());
        existingUser.setGender(userDto.getGender());
        userRepository.save(existingUser);

        userDto.setId(existingUser.getId());
        return userDto;

    }

    @Override
    public UserDTO createUser(UserDTO userDto) {


        if (userRepository.existsByDocumentNumber(userDto.getDocumentNumber())) {
            throw new IllegalArgumentException("El número de documento ya está registrado con otro usuario.");
        }

        UserEntity user = userMapper.toEntityByUserDTO(userDto);
        verifyRoles(userDto, user);

        if (userDto.getBloodBankId() == null) {
            user.setBloodBank(null);
        } else {
            BloodBankEntity bloodBank = bloodBankService.getBloodBankEntityById(userDto.getBloodBankId())
                    .orElseThrow(() -> new IllegalArgumentException("Banco de sangre no encontrado."));
            user.setBloodBank(bloodBank);
        }
        UserEntity userSaved = userRepository.save(user);
        userDto.setId(userSaved.getId());
        return userDto;
    }



    @Override
    public UserListDTO changeStatus(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setStatus(user.getStatus() == UserStatus.ACTIVE ? UserStatus.INACTIVE : UserStatus.ACTIVE);
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.toListDto(updatedUser);
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

    private void verifyRoles(UserDTO userDto, UserEntity user) {
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            user.setRoles(null);
        } else {
            Set<RoleEntity> roles = new HashSet<>(roleService.getRolesByIds(userDto.getRoles()));
            if (roles.isEmpty()) {
                throw new IllegalArgumentException("Error: No se encontraron roles válidos.");
            }
            user.setRoles(roles);
        }
    }
}