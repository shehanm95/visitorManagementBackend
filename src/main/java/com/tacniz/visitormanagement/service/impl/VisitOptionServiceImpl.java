package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.repo.VisitTypeRepo;
import com.tacniz.visitormanagement.service.ImageService;
import com.tacniz.visitormanagement.service.VisitOptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitOptionServiceImpl implements VisitOptionService {

    private final VisitOptionRepository visitOptionRepository;
    private final ObjectMapper objectMapper;
    private final VisitTypeRepo visitTypeRepository;
    private final ImageService imageService;
    private final VisitOptionMapper visitOptionMapper;
    private final Logger logger = LoggerFactory.getLogger(VisitOptionService.class);
    private final UserEntityRepository userEntityRepository;
    private final String IMAGE_DIRECTORY  = "visitOptionCovers/";

    @Override
    public VisitOptionDTO createVisitOption(VisitOptionDTO visitOptionDTO) {
        VisitType visitType = visitTypeRepository.findById(visitOptionDTO.getVisitType().getId())
                .orElseThrow(() -> new IllegalArgumentException("VisitType not found with id: " + visitOptionDTO.getVisitType().getId()));

        VisitOption visitOption = objectMapper.convertValue(visitOptionDTO, VisitOption.class);
        visitOption.setVisitType(visitType);
        visitOption.setDynamicQuestions(Collections.emptyList());
        visitOption = visitOptionRepository.save(visitOption);

        //saving image
        String imageName = imageService.saveImage(IMAGE_DIRECTORY, visitOption.getId().toString(), visitOptionDTO.getImage());
        visitOption.setImageName(imageName);
        VisitOption savedVisitOption = visitOptionRepository.save(visitOption);
        return objectMapper.convertValue(savedVisitOption, VisitOptionDTO.class);
    }

    @Override
    public VisitOptionDTO getVisitOptionById(Long id) {
        VisitOption visitOption = visitOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VisitOption not found with id: " + id));
        return objectMapper.convertValue(visitOption, VisitOptionDTO.class);
    }

    @Override
    public List<VisitOptionDTO> getAllVisitOptions() {
        return visitOptionRepository.findAll().stream()
                .map(visitorOption -> objectMapper.convertValue(visitorOption,VisitOptionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitOptionDTO> getVisitOptionsByVisitType(Long visitTypeId) {
        return visitOptionRepository.findByVisitTypeId(visitTypeId).stream()
                .map(visitorOption -> objectMapper.convertValue(visitorOption,VisitOptionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VisitOptionDTO updateVisitOption( VisitOptionDTO visitOptionDTO) {
        VisitOption visitOption = visitOptionRepository.findById(visitOptionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("VisitOption not found with id: " + visitOptionDTO.getId()));

        VisitType visitType = visitTypeRepository.findById(visitOptionDTO.getVisitType().getId())
                .orElseThrow(() -> new IllegalArgumentException("VisitType not found with id: " + visitOptionDTO.getVisitType().getId()));

        visitOption = visitOptionMapper.toEntity(visitOptionDTO);

        // Handle updated cover image if provided
        if (visitOptionDTO.getImage() != null && !visitOptionDTO.getImage().isEmpty()) {
            if(visitOption.getImageName() != null && !visitOption.getImageName().isEmpty()) imageService.deleteImage(IMAGE_DIRECTORY, visitOption.getImageName());
            String imageName = imageService.saveImage(IMAGE_DIRECTORY, visitOption.getId().toString(), visitOptionDTO.getImage());
            visitOption.setImageName(imageName);

        }
        visitOption = visitOptionRepository.save(visitOption);
        return visitOptionMapper.toDto(visitOption);
    }

    @Override
    public void deleteVisitOption(Long id) {
        VisitOption visitOption = visitOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VisitOption not found with id: " + id));
        imageService.deleteImage(IMAGE_DIRECTORY, visitOption.getImageName());
        visitOptionRepository.delete(visitOption);
    }

    @Override
    public ResponseEntity<Resource> getImage(String filename) {
        return imageService.getImage(IMAGE_DIRECTORY , filename);
    }

    @Override
    public VisitOptionDTO deleteCover(String fileName) {

        if(imageService.deleteImage(IMAGE_DIRECTORY,fileName)){
            try{
                System.out.println(fileName);
                String id =  fileName.split("\\.")[0];
                VisitOption visitOption = visitOptionRepository.findById(Long.valueOf(id)).get();
                visitOption.setImageName(null);
                visitOptionRepository.save(visitOption);
                return visitOptionMapper.toDto(visitOption);
            }catch (Exception e){
               logger.error("tried to reset the image name of but its not available.");
            }
        }
        return null;
    }


}