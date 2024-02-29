package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.domain.dto.SectionDto;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionMapper {

    private final ProjectRepository projectRepository;

    public SectionEntity mapToSectionEntity(SectionDto sectionDto){
        return SectionEntity.builder()
                .id(sectionDto.getId())
                .name(sectionDto.getName())
                .project(projectRepository.findById(sectionDto.getProjectId()).orElseGet(null))
                .build();
    }

    public SectionDto mapToSectionDto(SectionEntity sectionEntity){
        return SectionDto.builder()
                .id(sectionEntity.getId())
                .name(sectionEntity.getName())
                .projectId(sectionEntity.getProject().getId())
                .build();
    }

    public List<SectionDto> mapToSectionDtoList(List<SectionEntity> sectionEntities){
        return sectionEntities.stream()
                .map(this::mapToSectionDto)
                .collect(Collectors.toList());
    }

}
