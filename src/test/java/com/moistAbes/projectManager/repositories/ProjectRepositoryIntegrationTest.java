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
public class ProjectRepositoryIntegrationTest {

    private ProjectRepository underTest;

    @Autowired
    public ProjectRepositoryIntegrationTest(ProjectRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatProjectCanBeCreatedAndRecalled(){
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);
        projectEntityA.setId(savedProjectEntity.getId());
        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(projectEntityA);

        underTest.deleteById(savedProjectEntity.getId());
    }

    @Test
    public void testThatMultipleProjectCanBeCreatedAndRecalled(){
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity projectEntityB = TestDataUtil.createTestProjectB();
        ProjectEntity projectEntityC = TestDataUtil.createTestProjectC();

        ProjectEntity savedProjectEntityA = underTest.save(projectEntityA);
        ProjectEntity savedProjectEntityB = underTest.save(projectEntityB);
        ProjectEntity savedProjectEntityC = underTest.save(projectEntityC);

        projectEntityA.setId(savedProjectEntityA.getId());
        projectEntityB.setId(savedProjectEntityB.getId());
        projectEntityC.setId(savedProjectEntityC.getId());

        Optional<ProjectEntity> resultA = underTest.findById(savedProjectEntityA.getId());
        Optional<ProjectEntity> resultB = underTest.findById(savedProjectEntityB.getId());
        Optional<ProjectEntity> resultC = underTest.findById(savedProjectEntityC.getId());

        assertThat(resultA).isPresent();
        assertThat(resultB).isPresent();
        assertThat(resultC).isPresent();

        assertThat(resultA.get()).isEqualTo(projectEntityA);
        assertThat(resultB.get()).isEqualTo(projectEntityB);
        assertThat(resultC.get()).isEqualTo(projectEntityC);

        underTest.deleteById(savedProjectEntityA.getId());
        underTest.deleteById(savedProjectEntityB.getId());
        underTest.deleteById(savedProjectEntityC.getId());
    }

    @Test
    public void testThatProjectCanBeUpdated(){
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity savedProject = underTest.save(projectEntityA);
        savedProject.setTitle("UPDATED");
        ProjectEntity updatedProjectEntity = underTest.save(savedProject);

        Optional<ProjectEntity> result = underTest.findById(updatedProjectEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedProject);

        underTest.deleteById(updatedProjectEntity.getId());
    }

    @Test
    public void testThatProjectCanBeDeleted(){
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA();
        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);
        underTest.deleteById(savedProjectEntity.getId());

        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        assertThat(result).isEmpty();
    }
}