package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SectionRepositoryTests {

    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public SectionRepositoryTests(SectionRepository sectionRepository, ProjectRepository projectRepository) {
        this.sectionRepository = sectionRepository;
        this.projectRepository = projectRepository;
    }


    @Test
    void testThatSectionCanBeCreatedAndRecalled() {
        //Given
        ProjectEntity savedProject = projectRepository.save(TestDataUtil.createTestProjectA());
        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);

        //When
        SectionEntity savedSection = sectionRepository.save(testSection);
        Optional<SectionEntity> resultSection = sectionRepository.findById(savedSection.getId());

        //Then
        assertThat(resultSection).isPresent();
        assertThat(resultSection.get().getName()).isEqualTo(savedSection.getName());
        assertThat(resultSection.get().getProject().getId()).isEqualTo(savedSection.getProject().getId());

        //Clean up
        sectionRepository.deleteById(savedSection.getId());
        projectRepository.deleteById(savedProject.getId());
    }

    @Test
    void testThatMultipleSectionsCanBeCreatedAndRecalled() {
        //Given
        ProjectEntity savedProject = projectRepository.save(TestDataUtil.createTestProjectA());
        SectionEntity testSectionA = TestDataUtil.createSectionA(savedProject);
        SectionEntity testSectionB = TestDataUtil.createSectionB(savedProject);
        SectionEntity testSectionC = TestDataUtil.createSectionC(savedProject);

        //When
        SectionEntity savedSectionA = sectionRepository.save(testSectionA);
        SectionEntity savedSectionB = sectionRepository.save(testSectionB);
        SectionEntity savedSectionC = sectionRepository.save(testSectionC);

        Optional<SectionEntity> resultSectionA = sectionRepository.findById(savedSectionA.getId());
        Optional<SectionEntity> resultSectionB = sectionRepository.findById(savedSectionB.getId());
        Optional<SectionEntity> resultSectionC = sectionRepository.findById(savedSectionC.getId());

        //Then
        assertThat(resultSectionA).isPresent();
        assertThat(resultSectionA.get().getName()).isEqualTo(savedSectionA.getName());
        assertThat(resultSectionA.get().getProject().getId()).isEqualTo(savedSectionA.getProject().getId());

        assertThat(resultSectionB).isPresent();
        assertThat(resultSectionB.get().getName()).isEqualTo(savedSectionB.getName());
        assertThat(resultSectionB.get().getProject().getId()).isEqualTo(savedSectionB.getProject().getId());

        assertThat(resultSectionC).isPresent();
        assertThat(resultSectionC.get().getName()).isEqualTo(savedSectionC.getName());
        assertThat(resultSectionC.get().getProject().getId()).isEqualTo(savedSectionC.getProject().getId());

        //Clean up
        sectionRepository.deleteById(savedSectionA.getId());
        sectionRepository.deleteById(savedSectionB.getId());
        sectionRepository.deleteById(savedSectionC.getId());
        projectRepository.deleteById(savedProject.getId());
    }

    @Test
    void testThatSectionCanBeUpdated() {
        //Given
        ProjectEntity savedProject = projectRepository.save(TestDataUtil.createTestProjectA());
        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        testSection.setName("Updated name");

        //When
        SectionEntity updatedSection = sectionRepository.save(testSection);
        Optional<SectionEntity> resultSection = sectionRepository.findById(updatedSection.getId());

        //Then
        assertThat(resultSection).isPresent();
        assertThat(resultSection.get().getName()).isEqualTo("Updated name");
        assertThat(resultSection.get().getProject().getId()).isEqualTo(updatedSection.getProject().getId());

        //Clean up
        sectionRepository.deleteById(updatedSection.getId());
        projectRepository.deleteById(savedProject.getId());
    }

    @Test
    void testThatSectionCanBeDeleted() {
        //Given
        ProjectEntity savedProject = projectRepository.save(TestDataUtil.createTestProjectA());
        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        Long savedSectionId = sectionRepository.save(testSection).getId();

        //When
        sectionRepository.deleteById(savedSectionId);
        Optional<SectionEntity> resultSection = sectionRepository.findById(savedSectionId);

        //Then
        assertThat(resultSection).isEmpty();

        //Clean up
        projectRepository.deleteById(savedProject.getId());
    }
}
