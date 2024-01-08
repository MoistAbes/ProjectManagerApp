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
        List<TaskEntity> testTaskList = TestDataUtil.createTestTaskListA();
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA(testTaskList);

        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);

        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(projectEntityA);

        underTest.deleteById(savedProjectEntity.getId());
    }

    @Test
    public void testThatMultipleProjectCanBeCreatedAndRecalled(){
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        List<TaskEntity> testTaskListB = TestDataUtil.createTestTaskListA();
        List<TaskEntity> testTaskListC = TestDataUtil.createTestTaskListA();

        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA(testTaskListA);
        ProjectEntity projectEntityB = TestDataUtil.createTestProjectB(testTaskListB);
        ProjectEntity projectEntityC = TestDataUtil.createTestProjectC(testTaskListC);


        ProjectEntity savedProjectEntityA = underTest.save(projectEntityA);
        ProjectEntity savedProjectEntityB = underTest.save(projectEntityB);
        ProjectEntity savedProjectEntityC = underTest.save(projectEntityC);


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
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA(testTaskListA);
        underTest.save(projectEntityA);
        projectEntityA.setTitle("UPDATED");
        ProjectEntity updatedProjectEntity = underTest.save(projectEntityA);

        Optional<ProjectEntity> result = underTest.findById(projectEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(projectEntityA);

        underTest.deleteById(updatedProjectEntity.getId());
    }

    @Test
    public void testThatProjectCanBeDeleted(){
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity projectEntityA = TestDataUtil.createTestProjectA(testTaskListA);
        ProjectEntity savedProjectEntity = underTest.save(projectEntityA);
        underTest.deleteById(savedProjectEntity.getId());

        Optional<ProjectEntity> result = underTest.findById(savedProjectEntity.getId());

        assertThat(result).isEmpty();
    }
}
