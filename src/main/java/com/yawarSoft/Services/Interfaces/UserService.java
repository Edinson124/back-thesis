package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.UserEntity;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface UserService {
    Page<UserDTO> getUsersPaginated(int page, int size, String search, String role, String status);
    UserEntity getUserById(Long id);
    UserEntity updateUser(Long id, UserEntity userDetails);
    UserEntity createUser(UserEntity user, Set<Long> roleIds);
    void deactivateUser(Long userId);
}