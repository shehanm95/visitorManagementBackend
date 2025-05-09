package com.tacniz.visitormanagement.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Service interface for handling image-related operations, such as saving and deleting images.
 */
public interface ImageService {


    /**
     * @param directoryName ex "coverImage/"
     * @param imageNameWithoutExtension "1"
     * @param image multipart image
     * @return image name with the extension
     */
    String saveImage(String directoryName, String imageNameWithoutExtension, MultipartFile image);


    boolean deleteImage(String directoryName, String fileNameWithExtension);


    ResponseEntity<Resource> getImage(String directoryName, String fileNameWithExtension);

    default public String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }
}