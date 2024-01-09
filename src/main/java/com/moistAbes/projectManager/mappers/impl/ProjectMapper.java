package com.moistAbes.projectManager.mappers.impl;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper implements Mapper<ProjectEntity, ProjectDto> {

    private ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectDto mapToDto(ProjectEntity projectEntity) {
        return modelMapper.map(projectEntity, ProjectDto.class);
    }

    @Override
    public ProjectEntity mapToEntity(ProjectDto projectDto) {
        return modelMapper.map(projectDto, ProjectEntity.class);
    }

    public List<ProjectDto> mapToDtoList(List<ProjectEntity> projectEntities){
        return projectEntities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
