package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.IdVisitOptionObject;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.mapper.ServicePointMapper;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.model.ServicePoint;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.ServicePointRepository;
import com.tacniz.visitormanagement.repo.VisitTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitOptionMapperImpl implements VisitOptionMapper {
    private final ObjectMapper objectMapper;
    private final VisitTypeRepo visitTypeRepo;
    private final DynamicQuestionMapper dynamicQuestionMapper;


    @Autowired
    @Lazy
    private ServicePointMapper servicePointMapper;
    @Autowired
    @Lazy
    private ServicePointRepository servicePointRepository;


    @Override
    public VisitOption toEntity (VisitOptionDTO visitOptionDTO){
        if(visitOptionDTO == null) return null;
        VisitOption visitOption =   objectMapper.convertValue(visitOptionDTO, VisitOption.class);



        visitOption.setVisitType(objectMapper.convertValue(visitOptionDTO.getVisitType(), VisitType.class));
        return visitOption;
    }

    @Override
    public VisitOptionDTO toDto(VisitOption visitOptionEntity) {
        if (visitOptionEntity == null) return null;
        VisitOptionDTO visitOptionDTO = objectMapper.convertValue(visitOptionEntity, VisitOptionDTO.class);
        if(visitOptionDTO.getVisitType() != null) {
            VisitType visitType = visitTypeRepo.findById(visitOptionDTO.getVisitType().getId()).orElse(null);
            visitOptionDTO.setVisitType(objectMapper.convertValue(visitType, VisitTypeDTO.class));
        }

        List<ServicePoint> servicePoints = servicePointRepository.findByVisitOptionId(visitOptionDTO.getId());
        visitOptionDTO.setServicePoints(servicePointMapper.toDtoList(servicePoints));




        visitOptionDTO.setDynamicQuestions(visitOptionEntity.getDynamicQuestions().stream().map(dynamicQuestionMapper::toDto).toList());
        return visitOptionDTO;
    }

    @Override
    public IdVisitOptionObject toIdObject(VisitOption visitOption) {
        return  objectMapper.convertValue(visitOption, IdVisitOptionObject.class);
    }

    public void updateVisitOptionFromDto(VisitOptionDTO dto, VisitOption entity) {
        if (dto.getVisitOptionName() != null) {
            entity.setVisitOptionName(dto.getVisitOptionName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getIsPreRegistration() != null) {
            entity.setIsPreRegistration(dto.getIsPreRegistration());
        }
        if (dto.getImageName() != null) {
            entity.setImageName(dto.getImageName());
        }
        if (dto.getIsPhotoRequired() != null) {
            entity.setIsPhotoRequired(dto.getIsPhotoRequired());
        }
        if (dto.getIsPhotoOptional() != null) {
            entity.setIsPhotoOptional(dto.getIsPhotoOptional());
        }
        if (dto.getIsPhoneNumberRequired() != null) {
            entity.setIsPhoneNumberRequired(dto.getIsPhoneNumberRequired());
        }
        if (dto.getIsEmailRequired() != null) {
            entity.setIsEmailRequired(dto.getIsEmailRequired());
        }

        entity.setAverageTimeForAPerson(dto.getAverageTimeForAPerson());
        entity.setVisitorsPerRow(dto.getVisitorsPerRow());

        if (dto.getVisitDateType() != null) {
            entity.setVisitDateType(dto.getVisitDateType());
        }

        entity.setIsActive(dto.isActive());

        // You can add more fields if needed, like lists (timeRanges, dynamicQuestions, etc.)
    }

}
