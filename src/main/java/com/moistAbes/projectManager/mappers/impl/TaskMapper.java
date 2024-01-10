package com.moistAbes.projectManager.mappers.impl;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper implements Mapper<TaskEntity, TaskDto> {

    private ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto mapToDto(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity mapToEntity(TaskDto taskDto) {
        return modelMapper.map(taskDto, TaskEntity.class);
    }

    public List<TaskDto> mapToDtoList(List<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
