package com.yawarSoft.Modules.Admin.Services.Implemenations;
import com.yawarSoft.Core.Services.Interfaces.AuthenticatedUserService;
import com.yawarSoft.Core.Services.Interfaces.ImageStorageService;
import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.UserDTO;
import com.yawarSoft.Modules.Admin.Dto.UserListDTO;
import com.yawarSoft.Core.Entities.AuthEntity;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Core.Entities.RoleEntity;
import com.yawarSoft.Core.Entities.UserEntity;
import com.yawarSoft.Modules.Admin.Dto.UserSelectOptionDTO;
import com.yawarSoft.Modules.Admin.Enums.RoleEnum;
import com.yawarSoft.Modules.Admin.Enums.UserStatus;
import com.yawarSoft.Modules.Admin.Mappers.UserMapper;
import com.yawarSoft.Modules.Admin.Repositories.Projections.UserProjectionSelect;
import com.yawarSoft.Modules.Admin.Services.Interfaces.BloodBankService;
import com.yawarSoft.Modules.Admin.Services.Interfaces.RoleService;
import com.yawarSoft.Modules.Admin.Services.Interfaces.UserService;
import com.yawarSoft.Modules.Login.Services.Interfaces.AuthService;
import com.yawarSoft.Modules.Admin.Repositories.UserRepository;
import com.yawarSoft.Core.Utils.PasswordGenerator;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BloodBankService bloodBankService;
    private final AuthService authService;
    private final AuthenticatedUserService authenticatedUserService;
    private final UserMapper userMapper;
    private final ImageStorageService imageStorageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleService roleService, AuthService authService, UserMapper userMapper,
                           BloodBankService bloodBankService, AuthenticatedUserService authenticatedUserService,
                           ImageStorageService imageStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.authService = authService;
        this.bloodBankService = bloodBankService;
        this.userMapper = userMapper;
        this.authenticatedUserService = authenticatedUserService;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public Page<UserListDTO> getUsersPaginated(int page, int size, String search, Integer role, String status) {
        Pageable pageable = PageRequest.of(page, size);

        search = (search != null && !search.isBlank()) ? search : null;
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
    @Transactional
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

        String randomPassword = PasswordGenerator.generateRandomPassword();

        AuthEntity auth = new AuthEntity();
        auth.setUsername(user.getDocumentNumber()); // Usar número de documento como username
        // TODO: cambiar a randomPassword
        auth.setPassword(passwordEncoder.encode(randomPassword));
//        auth.setPassword(passwordEncoder.encode("1234"));
        auth.setUser(userSaved);
        authService.saveAuth(auth);

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
    public Boolean existsByDocument(Integer userId, String documentNumber) {
        if (userId != null) {
            Optional<UserEntity> existingUser = userRepository.findById(userId);
            if (existingUser.isEmpty()) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
            }
            if (existingUser.get().getDocumentNumber().equals(documentNumber)) {
                return false;
            }
        }
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public List<UserSelectOptionDTO> getMedicUsersByBloodBank(Integer idBloodBank) {
        List<UserProjectionSelect> userProjectionSelectList =
                userRepository.getUsersByRoleAndStatusAndBloodBank(idBloodBank, RoleEnum.MEDICO_BANCO_DE_SANGRE.getId(),UserStatus.ACTIVE);
        return userMapper.toSelectDtoListFromProjectionList(userProjectionSelectList);
    }

    @Override
    public List<UserSelectOptionDTO> getMedicRequestUsers() {
        UserEntity userAuth = authenticatedUserService.getCurrentUser();
        Integer idBloodBank = userAuth.getBloodBank().getId();
        List<UserProjectionSelect> userProjectionSelectList =
                userRepository.getUsersByRoleAndStatusAndBloodBank(idBloodBank, RoleEnum.MEDICO.getId(),UserStatus.ACTIVE);
        return userMapper.toSelectDtoListFromProjectionList(userProjectionSelectList);
    }

    @Override
    public String updateUserProfileImage(Integer userId, MultipartFile profileImage) throws IOException {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Guardar la imagen y obtener la URL
            String imageUrl = imageStorageService.storeImage(userId,profileImage,"BLOODBANK");
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
        if (userDto.getRoleId() == null) {
            user.setRole(null);
        } else {
            Set<RoleEntity> roles = new HashSet<>(roleService.getRolesByIds(Collections.singleton(userDto.getRoleId())));
            if (roles.isEmpty()) {
                throw new IllegalArgumentException("Error: No se encontraron roles válidos.");
            }
            user.setRole(roles.iterator().next());
        }
    }
}