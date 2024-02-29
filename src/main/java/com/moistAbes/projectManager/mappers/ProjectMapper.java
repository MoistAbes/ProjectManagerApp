package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.repositories.SectionRepository;
import com.moistAbes.projectManager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMapper {

    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;

    public ProjectEntity mapToProjectEntity(final ProjectDto projectDto){

        System.out.println("TESCIK PROJECT DTO: " + projectDto);

        return ProjectEntity.builder()
                .id(projectDto.getId())
                .title(projectDto.getTitle())
                .users(userRepository.findAll().stream()
                        .filter(user -> projectDto.getUsersId().contains(user.getId()))
                        .collect(Collectors.toList()))
                .build();
    }

    public ProjectDto mapToProjectDto(final ProjectEntity projectEntity) {

        System.out.println("TESCIK PROJECT ENTITY: " + projectEntity);


        return ProjectDto.builder()
                .id(projectEntity.getId())
                .title(projectEntity.getTitle())
                .usersId(projectEntity.getUsers().stream()
                        .map(UserEntity::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<ProjectDto> mapToProjectDtoList(final List<ProjectEntity> projectEntities){
        return projectEntities.stream()
                .map(this::mapToProjectDto)
                .collect(Collectors.toList());
    }

}
