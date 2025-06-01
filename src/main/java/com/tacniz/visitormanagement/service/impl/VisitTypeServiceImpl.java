package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.VisitTypeMapper;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.VisitTypeRepo;
import com.tacniz.visitormanagement.service.ImageService;
import com.tacniz.visitormanagement.service.VisitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Configuration
@Component
public class VisitTypeServiceImpl implements VisitTypeService {

    private final VisitTypeRepo visitTypeRepository;
    private final ImageService imageService;
    private final VisitTypeMapper visitTypeMapper;



    private final String MAIN_DIRECTORY = "visitTypeCovers/";
    private final ObjectMapper objectMapper;

    @Override
    public VisitTypeDTO createVisitType(VisitTypeDTO visitTypeDTO) throws IOException {
        VisitType visitType = VisitType.builder()
                .visitTypeName(visitTypeDTO.getVisitTypeName())
                .visitTypeDescription(visitTypeDTO.getVisitTypeDescription())
                .build();

        VisitType savedVisitType = visitTypeRepository.save(visitType);

        // Save the image after getting the ID
        String imagePath = imageService.saveImage(MAIN_DIRECTORY, savedVisitType.getId().toString(),visitTypeDTO.getImage() );
        savedVisitType.setImageName(imagePath);
        visitTypeRepository.save(savedVisitType);

        return objectMapper.convertValue(visitTypeRepository.save(savedVisitType),VisitTypeDTO.class);
    }

    @Override
    public VisitTypeDTO getVisitTypeById(Long id) {
        VisitType visitType = visitTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VisitType not found with id: " + id));
        return objectMapper.convertValue(visitTypeRepository.save(visitType),VisitTypeDTO.class);
    }

    @Override
    public List<VisitTypeDTO> getAllVisitTypes() {
        return visitTypeRepository.findAll().stream()
                .map(visitTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitTypeDTO updateVisitType(VisitTypeDTO visitTypeDTO) throws IOException {
        VisitType visitTypeEntity = visitTypeRepository.findById(visitTypeDTO.getId())
                .orElseThrow(() -> new RuntimeException("VisitType not found with id: " + visitTypeDTO.getId()));

        // Delete old image if exists
        String imagePath;
        if(visitTypeDTO.getImage() != null){
        imageService.deleteImage(MAIN_DIRECTORY,visitTypeEntity.getImageName());
        imagePath = imageService.saveImage(MAIN_DIRECTORY, visitTypeEntity.getId().toString(),visitTypeDTO.getImage());
        visitTypeEntity.setImageName(imagePath);
        }

        visitTypeEntity.setVisitTypeName(visitTypeDTO.getVisitTypeName());
        visitTypeEntity.setVisitTypeDescription(visitTypeDTO.getVisitTypeDescription());

        return objectMapper.convertValue(visitTypeRepository.save(visitTypeEntity),VisitTypeDTO.class);
    }

    @Override
    public void deleteVisitType(Long id) {
        VisitType visitType = visitTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VisitType not found with id: " + id));

        // Delete image file
        String imageName = visitType.getImageName().substring(visitType.getImageName().lastIndexOf("\\."));
        imageService.deleteImage(MAIN_DIRECTORY,imageName);

        visitTypeRepository.delete(visitType);
    }

    @Override
    public VisitType deleteCover(String filename) {
        System.out.println("fileName" + filename);
        VisitType visitType;
        try {
            Long typeId = Long.valueOf(filename.split("\\.")[0]);
            visitType = visitTypeRepository.getReferenceById(typeId);
            if(imageService.deleteImage(MAIN_DIRECTORY, visitType.getImageName())){
                visitType.setImageName(null);
                return visitTypeRepository.save(visitType);
            }
        }catch (Exception e){
            throw new IllegalArgumentException("wrong file name");
        }
            return null;
    }

    @Override
    public ResponseEntity<Resource> getImage(String filename) {
        return imageService.getImage(MAIN_DIRECTORY,filename);
    }
}