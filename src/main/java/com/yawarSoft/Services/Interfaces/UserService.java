package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Entities.UserEntity;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface UserService {
    Page<UserEntity> getUsersPaginated(int page, int size);
    UserEntity getUserById(Long id);
    UserEntity updateUser(Long id, UserEntity userDetails);
    UserEntity createUser(UserEntity user, Set<Long> roleIds);
    void deactivateUser(Long userId);
}