package com.content_service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUtil {

    @Value("${app.upload.dir:content-service/src/uploads/}")
    private String uploadDir;

    @Value("${app.upload.max-size:5242880}") // 5MB por defecto
    private long maxFileSize;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    public String saveImage(MultipartFile imageFile) {
        validateImage(imageFile);

        try {
            // Generar nombre único para evitar conflictos
            String originalFileName = imageFile.getOriginalFilename();
            String extension = getFileExtension(originalFileName);
            String fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "." + extension;

            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            // Guardar archivo
            Path imagePath = uploadPath.resolve(fileName);
            Files.write(imagePath, imageFile.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }
    }

    public boolean deleteImage(String fileName) {
        try {
            if (fileName == null || fileName.trim().isEmpty()) {
                return false;
            }

            Path imagePath = Paths.get(uploadDir, fileName);
            return Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen: " + e.getMessage(), e);
        }
    }

    public String getImageUrl(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }
        return "/api/images/" + fileName;
    }


}