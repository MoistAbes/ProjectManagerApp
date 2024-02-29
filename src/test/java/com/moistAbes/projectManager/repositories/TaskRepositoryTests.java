package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryTests {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private SectionRepository sectionRepository;
    private TaskRepository underTest;

    @Autowired
    public TaskRepositoryTests(TaskRepository underTest, ProjectRepository projectRepository, UserRepository userRepository, SectionRepository sectionRepository) {
        this.underTest = underTest;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
    }

    @Test
    public void testThatTaskCanBeCreatedAndRecalled(){
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);
        SectionEntity savedSection = sectionRepository.save(TestDataUtil.createSectionA(savedProject));

        //When
        TaskEntity savedTask = underTest.save(TestDataUtil.createTestTaskA(savedProject, savedSection));
        Optional<TaskEntity> result = underTest.findById(savedTask.getId());

        //Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedTask);

        //Clean up
        underTest.deleteById(savedTask.getId());
        sectionRepository.deleteById(savedSection.getId());
        projectRepository.deleteById(savedProject.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testThatMultipleTasksCanBeCreatedAndRecalled(){
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity savedSection = sectionRepository.save(TestDataUtil.createSectionA(savedProject));

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity testTaskB = TestDataUtil.createTestTaskB(savedProject, savedSection);
        TaskEntity testTaskC = TestDataUtil.createTestTaskC(savedProject, savedSection);

        //When
        TaskEntity savedTestTaskA = underTest.save(testTaskA);
        TaskEntity savedTestTaskB = underTest.save(testTaskB);
        TaskEntity savedTestTaskC = underTest.save(testTaskC);

        Optional<TaskEntity> resultA = underTest.findById(savedTestTaskA.getId());
        Optional<TaskEntity> resultB = underTest.findById(savedTestTaskB.getId());
        Optional<TaskEntity> resultC = underTest.findById(savedTestTaskC.getId());

        //Then
        assertThat(resultA).isPresent();
        assertThat(resultB).isPresent();
        assertThat(resultC).isPresent();

        assertThat(resultA.get()).isEqualTo(testTaskA);
        assertThat(resultB.get()).isEqualTo(testTaskB);
        assertThat(resultC.get()).isEqualTo(testTaskC);

        //Clean up
        underTest.deleteById(savedTestTaskA.getId());
        underTest.deleteById(savedTestTaskB.getId());
        underTest.deleteById(savedTestTaskC.getId());
        sectionRepository.deleteById(savedSection.getId());
        projectRepository.deleteById(savedProject.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testThatTaskCanBeUpdated(){
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity savedSection = sectionRepository.save(TestDataUtil.createSectionA(savedProject));

        TaskEntity savedTestTaskA = underTest.save(TestDataUtil.createTestTaskA(savedProject, savedSection));
        savedTestTaskA.setTitle("UPDATED");

        //When
        TaskEntity updatedTestTaskA = underTest.save(savedTestTaskA);
        Optional<TaskEntity> result = underTest.findById(updatedTestTaskA.getId());

        //Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedTestTaskA);

        //Clean up
        underTest.deleteById(updatedTestTaskA.getId());
        sectionRepository.deleteById(savedSection.getId());
        projectRepository.deleteById(savedProject.getId());
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testThatTaskCanBeDeleted(){
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity savedSection = sectionRepository.save(TestDataUtil.createSectionA(savedProject));
        TaskEntity savedTestTaskA = underTest.save(TestDataUtil.createTestTaskA(savedProject, savedSection));

        //When
        underTest.deleteById(savedTestTaskA.getId());
        Optional<TaskEntity> result = underTest.findById(savedTestTaskA.getId());

        //Then
        assertThat(result).isEmpty();

        //Clean up
        sectionRepository.deleteById(savedSection.getId());
        projectRepository.deleteById(savedProject.getId());
        userRepository.deleteById(savedUser.getId());
    }
}
