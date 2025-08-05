package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.mapper.UserMapper;
import com.tacniz.visitormanagement.mapper.DutyMapper;
import com.tacniz.visitormanagement.mapper.SpecialNoteMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    private final ObjectMapper objectMapper;
    private final DutyMapper dutyMapper;
    private final SpecialNoteMapper specialNoteMapper;

    public UserMapperImpl(ObjectMapper objectMapper,
                          DutyMapper dutyMapper,
                          SpecialNoteMapper specialNoteMapper) {
        this.objectMapper = objectMapper;
        this.dutyMapper = dutyMapper;
        this.specialNoteMapper = specialNoteMapper;
    }

    @Override
    public UserDto toDto(UserEntity user) {
        if (user == null) return null;

        UserDto dto = objectMapper.convertValue(user, UserDto.class);
        System.out.println("user email verified :" +  user.getIsEmailVerified().toString());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);

        if (user.getReviewedNotes() != null) {
            dto.setReviewedNotes(specialNoteMapper.toDtoList(user.getReviewedNotes()));
        }

        if (user.getDuties() != null) {
            dto.setDuties(dutyMapper.toDtoList(user.getDuties()));
        }

        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;

        UserEntity user = objectMapper.convertValue(dto, UserEntity.class);



        if (dto.getReviewedNotes() != null) {
            user.setReviewedNotes(
                    specialNoteMapper.toEntityList(dto.getReviewedNotes()));
        }

        if (dto.getDuties() != null) {
            user.setDuties(dutyMapper.toEntityList(dto.getDuties()));
        }

        return user;
    }

    @Override
    public List<UserDto> toDtoList(List<UserEntity> users) {
        if (users == null) return null;
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> toEntityList(List<UserDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
