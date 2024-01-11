package com.moistAbes.projectManager.mappersv2;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMapper2 {

    public ProjectEntity mapToProjectEntity(final ProjectDto projectDto){

        return ProjectEntity.builder()
                .id(projectDto.getId())
                .title(projectDto.getTitle())
                .build();
    }

    public ProjectDto mapToProjectDto(final ProjectEntity projectEntity) {

        return  ProjectDto.builder()
                .id(projectEntity.getId())
                .title(projectEntity.getTitle())
                .build();
    }

    public List<ProjectDto> mapToProjectDtoList(final List<ProjectEntity> projectEntities){
        return projectEntities.stream()
                .map(this::mapToProjectDto)
                .collect(Collectors.toList());
    }

}
