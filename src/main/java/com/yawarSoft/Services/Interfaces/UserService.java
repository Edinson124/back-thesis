package com.yawarSoft.Services.Interfaces;

import com.yawarSoft.Dto.ApiResponse;
import com.yawarSoft.Dto.UserDTO;
import com.yawarSoft.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;


public interface UserService {
    Page<UserDTO> getUsersPaginated(int page, int size, String search, String role, String status);
    UserEntity getUserById(Long id);
    UserEntity updateUser(Long id, UserEntity userDetails);
    UserEntity createUser(UserEntity user, Set<Long> roleIds);
    UserDTO changeStatus(Long userId);
    String updateUserProfileImage(Long userId, MultipartFile profileImage) throws IOException;
    ResponseEntity<ApiResponse> deleteUserProfileImage(Long userId);
}