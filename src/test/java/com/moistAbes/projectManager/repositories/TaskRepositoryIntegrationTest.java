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
public class TaskRepositoryIntegrationTest {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private SectionRepository sectionRepository;
    private TaskRepository underTest;

    @Autowired
    public TaskRepositoryIntegrationTest(TaskRepository underTest, ProjectRepository projectRepository, UserRepository userRepository, SectionRepository sectionRepository) {
        this.underTest = underTest;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
    }

    @Test
    public void testThatTaskCanBeCreatedAndRecalled(){
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userRepository.save(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionRepository.save(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity savedTask = underTest.save(testTask);

        Optional<TaskEntity> result = underTest.findById(savedTask.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedTask);

        underTest.deleteById(savedTask.getId());
    }

    @Test
    public void testThatMultipleTasksCanBeCreatedAndRecalled(){
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userRepository.save(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionRepository.save(testSection);

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(testProject, savedSection);
        TaskEntity testTaskB = TestDataUtil.createTestTaskB(testProject, savedSection);
        TaskEntity testTaskC = TestDataUtil.createTestTaskC(testProject, savedSection);

        TaskEntity savedTestTaskA = underTest.save(testTaskA);
        TaskEntity savedTestTaskB = underTest.save(testTaskB);
        TaskEntity savedTestTaskC = underTest.save(testTaskC);

        Optional<TaskEntity> resultA = underTest.findById(savedTestTaskA.getId());
        Optional<TaskEntity> resultB = underTest.findById(savedTestTaskB.getId());
        Optional<TaskEntity> resultC = underTest.findById(savedTestTaskC.getId());

        assertThat(resultA).isPresent();
        assertThat(resultB).isPresent();
        assertThat(resultC).isPresent();

        assertThat(resultA.get()).isEqualTo(testTaskA);
        assertThat(resultB.get()).isEqualTo(testTaskB);
        assertThat(resultC.get()).isEqualTo(testTaskC);

        underTest.deleteById(savedTestTaskA.getId());
        underTest.deleteById(savedTestTaskB.getId());
        underTest.deleteById(savedTestTaskC.getId());
    }

    @Test
    public void testThatTaskCanBeUpdated(){
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userRepository.save(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionRepository.save(testSection);

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(testProject, savedSection);
        TaskEntity savedTestTaskA = underTest.save(testTaskA);

        savedTestTaskA.setTitle("UPDATED");

        TaskEntity updatedTestTaskA = underTest.save(savedTestTaskA);
        Optional<TaskEntity> result = underTest.findById(updatedTestTaskA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedTestTaskA);

        underTest.deleteById(updatedTestTaskA.getId());
    }

    @Test
    public void testThatTaskCanBeDeleted(){
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userRepository.save(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionRepository.save(testSection);

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(testProject, savedSection);
        TaskEntity savedTestTaskA = underTest.save(testTaskA);

        underTest.deleteById(savedTestTaskA.getId());

        Optional<TaskEntity> result = underTest.findById(savedTestTaskA.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatAddUserWorksCorrectly(){

        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userRepository.save(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectRepository.save(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionRepository.save(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        testTask.setUsers(List.of(savedUser));
        TaskEntity savedTask = underTest.save(testTask);

        System.out.println("USERS: " + savedTask.getUsers());
        assertThat(savedTask.getUsers()).isNotEmpty();


    }
}
