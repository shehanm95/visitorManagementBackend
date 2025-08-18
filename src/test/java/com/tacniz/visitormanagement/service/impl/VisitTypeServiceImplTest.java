package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.VisitTypeMapper;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.repo.VisitTypeRepo;
import com.tacniz.visitormanagement.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class VisitTypeServiceImplTest {

    @Mock
    private VisitTypeRepo visitTypeRepo;

    @Mock
    private VisitOptionRepository visitOptionRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private VisitTypeMapper visitTypeMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private VisitTypeServiceImpl visitTypeService;

    private final String MAIN_DIRECTORY = "visitTypeCovers/";

    private VisitType visitType;
    private VisitTypeDTO visitTypeDTO;

    @BeforeEach
    void setup() {
        visitType = VisitType.builder()
                .id(1L)
                .visitTypeName("Type A")
                .visitTypeDescription("Description")
                .imageName("1.png")
                .isActive(true)
                .visitOptions(new ArrayList<>())
                .build();

        MockMultipartFile image = new MockMultipartFile("image", "1.png", "image/png", "test".getBytes());

        visitTypeDTO = VisitTypeDTO.builder()
                .id(1L)
                .visitTypeName("Type A")
                .visitTypeDescription("Description")
                .imageName("1.png")
                .isActive(true)
                .image(image)
                .build();

        ReflectionTestUtils.setField(visitTypeService, "MAIN_DIRECTORY", MAIN_DIRECTORY);
    }
    @Test
    void testCreateVisitType_success() throws IOException {
        when(visitTypeRepo.save(any())).thenReturn(visitType);
        when(imageService.saveImage(anyString(), anyString(), any())).thenReturn("1.png");
        when(objectMapper.convertValue(any(), eq(VisitTypeDTO.class))).thenReturn(visitTypeDTO);

        VisitTypeDTO result = visitTypeService.createVisitType(visitTypeDTO);

        assertNotNull(result);
        assertEquals("1.png", result.getImageName());
        verify(imageService).saveImage(eq(MAIN_DIRECTORY), eq("1"), any());
    }

    @Test
    void testGetVisitTypeById_found() {
        when(visitTypeRepo.findById(1L)).thenReturn(Optional.of(visitType));
        when(objectMapper.convertValue(visitType, VisitTypeDTO.class)).thenReturn(visitTypeDTO);

        VisitTypeDTO result = visitTypeService.getVisitTypeById(1L);

        assertNotNull(result);
        assertEquals("Type A", result.getVisitTypeName());
    }

    @Test
    void testGetVisitTypeById_notFound() {
        when(visitTypeRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> visitTypeService.getVisitTypeById(1L));

        assertEquals("VisitType not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetAllVisitTypes_success() {
        when(visitTypeRepo.findAll()).thenReturn(List.of(visitType));
        when(objectMapper.convertValue(visitType, VisitTypeDTO.class)).thenReturn(visitTypeDTO);

        List<VisitTypeDTO> result = visitTypeService.getAllVisitTypes();

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateVisitType_success() throws IOException {
        when(visitTypeRepo.findById(1L)).thenReturn(Optional.of(visitType));
        when(imageService.deleteImage(anyString(), anyString())).thenReturn(true);
        when(imageService.saveImage(anyString(), anyString(), any())).thenReturn("1.png");
        when(visitTypeRepo.save(any())).thenReturn(visitType);
        when(objectMapper.convertValue(any(), eq(VisitTypeDTO.class))).thenReturn(visitTypeDTO);

        VisitTypeDTO result = visitTypeService.updateVisitType(visitTypeDTO);

        assertNotNull(result);
        verify(imageService).deleteImage(eq(MAIN_DIRECTORY), any());
        verify(imageService).saveImage(eq(MAIN_DIRECTORY), eq("1"), any());
    }





    @Test
    void testDeleteCover_success() {
        when(visitTypeRepo.getReferenceById(1L)).thenReturn(visitType);
        when(imageService.deleteImage(anyString(), anyString())).thenReturn(true);
        when(visitTypeRepo.save(any())).thenReturn(visitType);

        VisitType result = visitTypeService.deleteCover("1.png");

        assertNotNull(result);
        assertNull(result.getImageName());
    }

    @Test
    void testDeleteCover_invalidFileName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> visitTypeService.deleteCover("invalid"));

        assertEquals("wrong file name", exception.getMessage());
    }

    @Test
    void testGetImage_success() {
        Resource resource = new ByteArrayResource("test".getBytes());
        when(imageService.getImage(anyString(), anyString()))
                .thenReturn(ResponseEntity.ok(resource));

        ResponseEntity<Resource> response = visitTypeService.getImage("1.png");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

//    @Test
//    void testDeleteVisitType_success() {
//        // Arrange
//        when(visitTypeRepo.findById(1L)).thenReturn(Optional.of(visitType));
//        when(imageService.deleteImage(anyString(), anyString())).thenReturn(true);
//
//        // Act
//        visitTypeService.deleteVisitType(1L);
//
//        // Assert
//        verify(visitTypeRepo).delete(visitType);
//        verify(imageService).deleteImage(MAIN_DIRECTORY, "1.png");
//    }
//
//    @Test
//    void testGetVisitTypesWithPreRegistration_success() {
//        // Arrange
//        List<VisitType> visitTypes = List.of(visitType);
//        List<VisitTypeDTO> dtoList = List.of(visitTypeDTO);
//
//        when(visitOptionRepository.findVisitTypesWithPreRegistrationAndActive()).thenReturn(visitTypes);
//        when(visitTypeMapper.toDtoList(visitTypes)).thenReturn(dtoList);
//
//        // Act
//        List<VisitTypeDTO> result = visitTypeService.getVisitTypesWithPreRegistration();
//
//        // Assert
//        assertEquals(1, result.size());
//        verify(visitOptionRepository).findVisitTypesWithPreRegistrationAndActive();
//        verify(visitTypeMapper).toDtoList(visitTypes);
//    }
}
