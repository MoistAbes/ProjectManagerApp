package com.moistAbes.projectManager.mappers.impl;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public List<UserDto> mapToDtoList(List<UserEntity> userEntities){
        return userEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
