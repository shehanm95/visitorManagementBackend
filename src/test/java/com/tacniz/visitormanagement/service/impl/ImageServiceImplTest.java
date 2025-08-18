package com.tacniz.visitormanagement.service.impl;

import org.junit.jupiter.api.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {

    private ImageServiceImpl imageService;
    private static final String TEST_DIRECTORY = "test-images/";
    private static final String BASE_PATH = "src/main/resources/";
    private static final Path TEST_DIRECTORY_PATH = Paths.get(BASE_PATH, TEST_DIRECTORY);

    @BeforeEach
    void setUp() throws IOException {
        imageService = new ImageServiceImpl();
        Files.createDirectories(TEST_DIRECTORY_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test directory after each test
        if (Files.exists(TEST_DIRECTORY_PATH)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(TEST_DIRECTORY_PATH)) {
                for (Path file : stream) {
                    Files.deleteIfExists(file);
                }
            }
            Files.deleteIfExists(TEST_DIRECTORY_PATH);
        }
    }

    @Test
    void saveImage_shouldSaveFile_andReturnFileName() throws IOException {
        String fileName = "testImage";
        String originalFileName = "test.png";
        byte[] content = "dummy image content".getBytes();

        MockMultipartFile file = new MockMultipartFile("image", originalFileName, "image/png", content);

        String returnedName = imageService.saveImage(TEST_DIRECTORY, fileName, file);

        Path savedFilePath = Paths.get(BASE_PATH, TEST_DIRECTORY, returnedName);
        assertTrue(Files.exists(savedFilePath));
        assertTrue(returnedName.endsWith(".png"));
    }

    @Test
    void saveImage_nullFile_returnsNull() {
        String result = imageService.saveImage(TEST_DIRECTORY, "img", null);
        assertNull(result);
    }

    @Test
    void deleteImage_existingFile_returnsTrue() throws IOException {
        String fileName = "toDelete.png";
        Path filePath = TEST_DIRECTORY_PATH.resolve(fileName);
        Files.write(filePath, "some content".getBytes());

        boolean deleted = imageService.deleteImage(TEST_DIRECTORY, fileName);
        assertTrue(deleted);
        assertFalse(Files.exists(filePath));
    }

    @Test
    void deleteImage_nonExistingFile_returnsTrue() {
        boolean deleted = imageService.deleteImage(TEST_DIRECTORY, "notFound.png");
        assertTrue(deleted); // Files.deleteIfExists returns false but we interpret as true for no error
    }



    @Test
    void getImage_existingFile_returnsResource() throws IOException {
        String fileName = "existing.png";
        Path filePath = TEST_DIRECTORY_PATH.resolve(fileName);
        Files.write(filePath, "test content".getBytes());

        ResponseEntity<Resource> response = imageService.getImage(TEST_DIRECTORY, fileName);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(Objects.requireNonNull(response.getBody()).exists());
    }

    @Test
    void getImage_nonExistingFile_returnsNotFound() {
        ResponseEntity<Resource> response = imageService.getImage(TEST_DIRECTORY, "doesNotExist.png");
        assertEquals(404, response.getStatusCodeValue());
    }


}
