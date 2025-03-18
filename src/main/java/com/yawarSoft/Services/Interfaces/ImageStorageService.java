package com.yawarSoft.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageStorageService {
    String storeImage(Long idUser, MultipartFile file) throws IOException;
    boolean deleteImage(String imagePath);
}
