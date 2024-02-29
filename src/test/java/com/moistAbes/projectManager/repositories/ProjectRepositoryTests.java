package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectRepositoryTests {

    private final ProjectRepository underTest;

    @Autowired
    public ProjectRepositoryTests(ProjectRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatProjectCanBeCreatedAndRecalled(){
        //Given
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();

        //When
        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);
        projectEntityA.setId(savedProjectEntity.getId());
        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        //Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(projectEntityA);

        //Clean up
        underTest.deleteById(savedProjectEntity.getId());
    }

    @Test
    public void testThatMultipleProjectCanBeCreatedAndRecalled(){
        //Given
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity projectEntityB = TestDataUtil.createTestProjectB();
        ProjectEntity projectEntityC = TestDataUtil.createTestProjectC();

        //When
        ProjectEntity savedProjectEntityA = underTest.save(projectEntityA);
        ProjectEntity savedProjectEntityB = underTest.save(projectEntityB);
        ProjectEntity savedProjectEntityC = underTest.save(projectEntityC);

        projectEntityA.setId(savedProjectEntityA.getId());
        projectEntityB.setId(savedProjectEntityB.getId());
        projectEntityC.setId(savedProjectEntityC.getId());

        Optional<ProjectEntity> resultA = underTest.findById(savedProjectEntityA.getId());
        Optional<ProjectEntity> resultB = underTest.findById(savedProjectEntityB.getId());
        Optional<ProjectEntity> resultC = underTest.findById(savedProjectEntityC.getId());

        //Then
        assertThat(resultA).isPresent();
        assertThat(resultB).isPresent();
        assertThat(resultC).isPresent();

        assertThat(resultA.get()).isEqualTo(projectEntityA);
        assertThat(resultB.get()).isEqualTo(projectEntityB);
        assertThat(resultC.get()).isEqualTo(projectEntityC);

        //Clean up
        underTest.deleteById(savedProjectEntityA.getId());
        underTest.deleteById(savedProjectEntityB.getId());
        underTest.deleteById(savedProjectEntityC.getId());
    }

    @Test
    public void testThatProjectCanBeUpdated(){
        //Given
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity savedProject = underTest.save(projectEntityA);
        savedProject.setTitle("UPDATED");

        //When
        ProjectEntity updatedProjectEntity = underTest.save(savedProject);
        Optional<ProjectEntity> result = underTest.findById(updatedProjectEntity.getId());

        //Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedProject);

        //Clean up
        underTest.deleteById(updatedProjectEntity.getId());
    }

    @Test
    public void testThatProjectCanBeDeleted(){
        //Given
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);

        //When
        underTest.deleteById(savedProjectEntity.getId());
        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        //Then
        assertThat(result).isEmpty();

        //Clean up
    }
}