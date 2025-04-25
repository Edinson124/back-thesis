package com.yawarSoft.Core.Services.Implementations;

import com.yawarSoft.Core.Services.Interfaces.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/img_profile/";

    @Override
    public String storeImage(Integer idUser, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío.");
        }

        // Crear directorio del usuario
        Path userDir = Paths.get(IMAGE_UPLOAD_DIR, idUser.toString());
        Files.createDirectories(userDir);

        // Generar nombre de archivo único
        String fileName = "profile.jpg"; // Puedes usar UUID si quieres nombres únicos
        Path filePath = userDir.resolve(fileName);

        // Guardar imagen
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }

    @Override
    public boolean deleteImage(String imagePath) {
        try {
            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, imagePath);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false; // Imagen no encontrada
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al eliminar
        }
    }
}
