package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.UserDTO;
import com.yawarSoft.Modules.Admin.Dto.UserListDTO;
import com.yawarSoft.Modules.Admin.Dto.UserSelectOptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {
    Page<UserListDTO> getUsersPaginated(int page, int size, String search, String role, String status);
    UserDTO getUserById(Integer id);
    UserDTO updateUser(Integer id, UserDTO userDTO);
    UserDTO createUser(UserDTO userDto);
    UserListDTO changeStatus(Integer userId);
    Boolean existsByDocument(String documentType, String documentNumber);
    List<UserSelectOptionDTO> getMedicUsersByBloodBank(Integer idBloodBank);
    String updateUserProfileImage(Integer userId, MultipartFile profileImage) throws IOException;
    ResponseEntity<ApiResponse> deleteUserProfileImage(Integer userId);
}