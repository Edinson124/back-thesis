package com.yawarSoft.Core.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageStorageService {
    String storeImage(Integer idUser, MultipartFile file, String root) throws IOException;
    boolean deleteImage(String imagePath);
}
