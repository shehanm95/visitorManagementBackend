package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.RegisterRequest;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.model.UserEntity;

import java.util.List;

public interface UserMapper {

    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> users);

    List<UserEntity> toEntityList(List<UserDto> dtos);

    void mapToUser(UserEntity user, RegisterRequest userDto);
}
