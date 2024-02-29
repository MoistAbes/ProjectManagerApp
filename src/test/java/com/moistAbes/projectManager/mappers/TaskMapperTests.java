package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import com.moistAbes.projectManager.services.impl.SectionServiceImpl;
import com.moistAbes.projectManager.services.impl.TaskServiceImpl;
import com.moistAbes.projectManager.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskMapperTests {

    //to do
    private final TaskMapper taskMapper;
    private final UserServiceImpl userService;
    private final ProjectServiceImpl projectService;
    private final SectionServiceImpl sectionService;
    private final TaskServiceImpl taskService;

    @Autowired
    public TaskMapperTests(TaskMapper taskMapper, UserServiceImpl userService, ProjectServiceImpl projectService, SectionServiceImpl sectionService, TaskServiceImpl taskService) {
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.projectService = projectService;
        this.sectionService = sectionService;
        this.taskService = taskService;
    }

    @Test
    void testThatMapperCorrectlyMapsFromDtoToEntity() throws ProjectNotFoundException, SectionNotFoundException {
        //Given
        UserEntity savedUser = userService.saveUser(TestDataUtil.createTestUserA());
        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity savedSection = sectionService.saveSection(TestDataUtil.createSectionA(savedProject));
        TaskEntity dependentTask = taskService.saveTask(TestDataUtil.createTestTaskA(savedProject, savedSection));

        TaskEntity testTask = TestDataUtil.createTestTaskB(savedProject, savedSection);
        TaskEntity savedTask = taskService.saveTask(testTask);

        TaskDto taskDto = TestDataUtil.createTaskDtoA();
        taskDto.setId(savedTask.getId());
        taskDto.setProjectId(savedProject.getId());
        taskDto.setUsers(List.of(savedUser.getId()));
        taskDto.setDependentTasks(List.of(dependentTask.getId()));
        taskDto.setSectionId(savedSection.getId());

        //When
        TaskEntity mappedTask = taskMapper.mapToTaskEntity(taskDto);

        //Then
        assertThat(mappedTask.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(mappedTask.getDependentTasks().size()).isEqualTo(taskDto.getDependentTasks().size());
        assertThat(mappedTask.getProject().getId()).isEqualTo(taskDto.getProjectId());
        assertThat(mappedTask.getUsers().size()).isEqualTo(taskDto.getUsers().size());

        //Clean up
        taskService.deleteTask(dependentTask.getId());
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatMapperCorrectlyMapsFromEntityToDto() {
        //Given
        UserEntity savedUser = userService.saveUser(TestDataUtil.createTestUserA());
        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity savedSection = sectionService.saveSection(TestDataUtil.createSectionA(savedProject));
        TaskEntity dependentTask = taskService.saveTask(TestDataUtil.createTestTaskA(savedProject, savedSection));

        TaskEntity testTask = TestDataUtil.createTestTaskB(savedProject, savedSection);
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        TaskEntity savedTask = taskService.saveTask(testTask);


        //When
        TaskDto mappedTask = taskMapper.mapToTaskDto(savedTask);

        //Then
        assertThat(mappedTask.getId()).isEqualTo(savedTask.getId());
        assertThat(mappedTask.getTitle()).isEqualTo(savedTask.getTitle());
        assertThat(mappedTask.getProjectId()).isEqualTo(savedTask.getProject().getId());
        assertThat(mappedTask.getSectionId()).isEqualTo(savedTask.getSection().getId());


        //Clean up
        taskService.deleteTask(dependentTask.getId());
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }
}
