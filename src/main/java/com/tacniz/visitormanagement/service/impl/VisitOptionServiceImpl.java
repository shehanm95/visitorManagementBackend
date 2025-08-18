package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.SpecificDateDto;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.model.SpecificDate;
import com.tacniz.visitormanagement.model.TimeRange;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.*;
import com.tacniz.visitormanagement.service.ImageService;
import com.tacniz.visitormanagement.service.VisitOptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final TimeRangeRepository timeRangeRepository;

    @Autowired
    @Lazy
    private final SpecificDateRepo specificDateRepo;

    @Override
    @Transactional
    public VisitOptionDTO createVisitOption(VisitOptionDTO visitOptionDTO) {
        // Validate input
        if (visitOptionDTO == null) {
            throw new IllegalArgumentException("VisitOptionDTO cannot be null");
        }

        System.out.println("$$$$$$$$$$$$$$$$$$$$");
        System.out.println(visitOptionDTO.isActive());
        System.out.println(visitOptionDTO.getIsPreRegistration());

        // CORRECTED: Using visitTypeRepository instead of visitOptionRepository
        VisitType visitType = visitTypeRepository.findById(visitOptionDTO.getVisitType().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "VisitType not found with id: " + visitOptionDTO.getVisitType().getId()));

        // Convert and set basic properties
        VisitOption visitOption = objectMapper.convertValue(visitOptionDTO, VisitOption.class);
        visitOption.setIsActive(visitOptionDTO.isActive());
        System.out.println("visit Option :" + visitOption.getIsActive());
        visitOption.setVisitType(visitType);
        visitOption.setDynamicQuestions(new ArrayList<>());

        // Handle time ranges
        if (visitOptionDTO.getTimeRanges() != null) {
            List<TimeRange> timeRanges = visitOptionDTO.getTimeRanges().stream()
                    .map(t -> {
                        TimeRange timeRange = objectMapper.convertValue(t, TimeRange.class);
                        timeRange.setVisitOption(visitOption);
                        return timeRange;
                    })
                    .collect(Collectors.toList());

            visitOption.setTimeRanges(timeRanges);
        }
        if(visitOptionDTO.getSpecificDates() != null){
            List<SpecificDate> specificDates = visitOptionDTO.getSpecificDates()
                    .stream()
                    .map(s->{
                        SpecificDate specificDate = objectMapper.convertValue(s,SpecificDate.class);
                        specificDate.setVisitOption(visitOption);
                        return specificDate;
                    }).collect(Collectors.toList());
          visitOption.setSpecificDates(specificDates);
        }

        // Save the visit option (with cascaded time ranges)
        VisitOption savedVisitOption = visitOptionRepository.save(visitOption);

        // Handle image saving
        if (visitOptionDTO.getImage() != null) {
            String imageName = imageService.saveImage(
                    IMAGE_DIRECTORY,
                    savedVisitOption.getId().toString(),
                    visitOptionDTO.getImage());
            savedVisitOption.setImageName(imageName);
            savedVisitOption = visitOptionRepository.save(savedVisitOption);
        }

        VisitOptionDTO savedVisitOptionDto =  objectMapper.convertValue(savedVisitOption, VisitOptionDTO.class);
        savedVisitOptionDto.setActive(savedVisitOption.getIsActive());
        return savedVisitOptionDto;
    }

    @Override
    public VisitOptionDTO getVisitOptionById(Long id) {
        VisitOption visitOption = visitOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VisitOption not found with id: " + id));

        VisitOptionDTO visitOptionDTO = objectMapper.convertValue(visitOption, VisitOptionDTO.class);
        List<SpecificDateDto> specificDates = specificDateRepo.findAllByVisitOptionId(visitOption.getId()).stream().map(s->objectMapper.convertValue(s, SpecificDateDto.class)).toList();
        visitOptionDTO.setSpecificDates(specificDates);
        return visitOptionDTO;
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

        VisitType visitType = visitOptionRepository.findVisitTypeByVisitOptionId(visitOptionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("VisitType not found with id: " + visitOptionDTO.getVisitType().getId()));

        System.out.println("Visit Type : " + visitType);
        visitOptionMapper.updateVisitOptionFromDto(visitOptionDTO, visitOption);
        visitOption.setVisitType(visitType);


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

    @Override
    public List<VisitOptionDTO> getActiveVisitOptionsByVisitTypeId(Long visitTypeId) {
        return visitOptionRepository
                .findByVisitTypeIdAndIsActiveTrue(visitTypeId)
                .stream()
                .map(t->objectMapper.convertValue(t,VisitOptionDTO.class
        )).collect(Collectors.toList());
    }

    @Override
    public List<VisitOptionDTO> ActiveAllPreRegOptionsByTypeId(Long visitTypeId){
       return visitOptionRepository.findByVisitTypeIdAndIsPreRegistrationTrueAndIsActiveTrue(visitTypeId)
               .stream()
               .map(o->objectMapper.convertValue(o,VisitOptionDTO.class))
               .collect(Collectors.toList());
    }

}