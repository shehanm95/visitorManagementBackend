package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private static final String SRC_MAIN_RESOURCES = "src/main/resources/";


    @Override
    public String saveImage(String directoryName, String imageNameWithoutExtension, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        // Create directory if it doesn't exist
        Path directoryPath = Paths.get(SRC_MAIN_RESOURCES,directoryName);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new IllegalArgumentException("Given image save directory path cannot be created  (ImageService) : "+e);
            }
        }

        // Get the file extension
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".png"; // Default to .png if no extension

        // Construct the file name as {id}.{extension}
        String directoryAndFileName = imageNameWithoutExtension + extension;
        Path filePath = Paths.get(SRC_MAIN_RESOURCES, directoryName , directoryAndFileName);

        // Save the file
        try {
            Files.write(filePath, image.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot get bytes from the image (ImageService) :"+e);
        }

        // Return the relative path to store in the database
        return directoryAndFileName.substring(directoryAndFileName.lastIndexOf("/")+1);
    }

    @Override
    public boolean deleteImage(String directoryName, String fileNameWithExtension) {
        if (fileNameWithExtension != null || !fileNameWithExtension.isEmpty() || directoryName != null || !directoryName.isEmpty()) {
            try {
                String imagePath = directoryName + fileNameWithExtension;
                Path filePath = Paths.get(SRC_MAIN_RESOURCES + imagePath);
                Files.deleteIfExists(filePath);
                return true;
            } catch (IOException e) {
                logger.error("Failed to delete image: " + directoryName + fileNameWithExtension);
            }
        }else {
            logger.error("No image to delete in :" + directoryName+fileNameWithExtension);
        }
        return false;
    }

    @Override
    public ResponseEntity<Resource> getImage(String directoryName, String fileNameWithExtension) {
        System.out.println("image requested" + directoryName + fileNameWithExtension);
        if (fileNameWithExtension == null || fileNameWithExtension.isEmpty() || directoryName == null || directoryName.isEmpty()) {
            logger.error("something wrong in request file path : " + directoryName + fileNameWithExtension );
        }

        String directoryNameAndFileNameWithExtension = directoryName + fileNameWithExtension;
        Path filePath = Paths.get(SRC_MAIN_RESOURCES, directoryNameAndFileNameWithExtension).normalize();

        try {
            // Create a UrlResource for the file
            UrlResource resource = new UrlResource(filePath.toUri());
            // Check if the resource exists and is readable
            if (!resource.exists() || !resource.isReadable()) {
            System.out.println("image found");
                return ResponseEntity.notFound().build();
            }

            // Determine the content type
            String contentType = determineContentType(directoryNameAndFileNameWithExtension);

            // Build and return the response
            String fileName = directoryNameAndFileNameWithExtension;
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            // Handle invalid URI syntax
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Handle other potential errors (e.g., I/O issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
