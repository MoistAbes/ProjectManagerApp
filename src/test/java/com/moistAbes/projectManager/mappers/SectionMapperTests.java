package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.SectionDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import com.moistAbes.projectManager.services.impl.SectionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SectionMapperTests {

    private final SectionMapper sectionMapper;
    private final ProjectServiceImpl projectService;
    private final SectionServiceImpl sectionService;

    @Autowired
    public SectionMapperTests(SectionMapper sectionMapper, ProjectServiceImpl projectService, SectionServiceImpl sectionService) {
        this.sectionMapper = sectionMapper;
        this.projectService = projectService;
        this.sectionService = sectionService;
    }

    @Test
    void testThatMapperCorrectlyMapsFromDtoToEntity() {
        //Given
        ProjectEntity savedProject = projectService.saveProject(TestDataUtil.createTestProjectA());

        SectionDto sectionDto = TestDataUtil.createDtoSectionA(savedProject.getId());

        //When
        SectionEntity mappedSection = sectionMapper.mapToSectionEntity(sectionDto);

        //Then
        assertThat(mappedSection.getName()).isEqualTo(sectionDto.getName());
        assertThat(mappedSection.getProject().getId()).isEqualTo(sectionDto.getProjectId());

        //Clean up
        projectService.deleteProject(savedProject.getId());
    }

    @Test
    void testThatMapperCorrectlyMapsFromEntityToDto() {
        //Given
        ProjectEntity savedProject = projectService.saveProject(TestDataUtil.createTestProjectA());
        SectionEntity savedSection = sectionService.saveSection(TestDataUtil.createSectionA(savedProject));

        //When
        SectionDto mappedSection = sectionMapper.mapToSectionDto(savedSection);

        //Then
        assertThat(mappedSection.getName()).isEqualTo(savedSection.getName());
        assertThat(mappedSection.getProjectId()).isEqualTo(savedSection.getProject().getId());

        //Clean up
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());

    }
}
