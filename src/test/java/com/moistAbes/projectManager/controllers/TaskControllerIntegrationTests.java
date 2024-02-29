package com.moistAbes.projectManager.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.mappers.TaskMapper;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import com.moistAbes.projectManager.services.impl.SectionServiceImpl;
import com.moistAbes.projectManager.services.impl.TaskServiceImpl;
import com.moistAbes.projectManager.services.impl.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TaskMapper taskMapper;
    private UserServiceImpl userService;
    private ProjectServiceImpl projectService;
    private SectionServiceImpl sectionService;
    private TaskServiceImpl taskService;

    @Autowired
    public TaskControllerIntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            TaskMapper taskMapper,
            UserServiceImpl userService,
            ProjectServiceImpl projectService,
            SectionServiceImpl sectionService,
            TaskServiceImpl taskService
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.projectService = projectService;
        this.sectionService = sectionService;
        this.taskService = taskService;
    }

    @Test
    void testThatGetTaskReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity savedTask = taskService.saveTask(testTask);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/" + savedTask.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetTaskReturnsCorrectTask() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity savedTask = taskService.saveTask(testTask);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/" + savedTask.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("TaskTestTitleA")
        );

        //Clean up
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetTasksReturnsHttp200() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
    }

    @Test
    void testThatGetTasksCorrectlyReturnsTasks() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity testTaskB = TestDataUtil.createTestTaskB(savedProject, savedSection);
        TaskEntity testTaskC = TestDataUtil.createTestTaskC(savedProject, savedSection);

        TaskEntity savedTaskA = taskService.saveTask(testTaskA);
        TaskEntity savedTaskB = taskService.saveTask(testTaskB);
        TaskEntity savedTaskC = taskService.saveTask(testTaskC);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].title", Matchers.allOf(hasItem("TaskTestTitleA"), hasItem("TaskTestTitleB"), hasItem("TaskTestTitleC")))
        );

        //Clean up
        taskService.deleteTask(savedTaskA.getId());
        taskService.deleteTask(savedTaskB.getId());
        taskService.deleteTask(savedTaskC.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());

    }

    @Test
    void testThatGetTaskWithProjectIdReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetTasksWithProjectIdReturnsCorrectTasks() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTaskA = TestDataUtil.createTestTaskA(savedProject, savedSection);
        TaskEntity testTaskB = TestDataUtil.createTestTaskB(savedProject, savedSection);
        TaskEntity testTaskC = TestDataUtil.createTestTaskC(savedProject, savedSection);

        TaskEntity savedTaskA = taskService.saveTask(testTaskA);
        TaskEntity savedTaskB = taskService.saveTask(testTaskB);
        TaskEntity savedTaskC = taskService.saveTask(testTaskC);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].title", Matchers.allOf(hasItem("TaskTestTitleA"), hasItem("TaskTestTitleB"), hasItem("TaskTestTitleC")))
        );

        //Clean up
        taskService.deleteTask(savedTaskA.getId());
        taskService.deleteTask(savedTaskB.getId());
        taskService.deleteTask(savedTaskC.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatCreateTaskReturnsHttp201() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskDto testTask = TestDataUtil.createTestTaskDtoA(savedProject.getId(), savedSection.getId());
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        String taskJson = objectMapper.writeValueAsString(testTask);

        //When & Then
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long taskId = jsonNode.get("id").asLong();

        taskService.deleteTask(taskId);
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatCreateTaskSuccesfullyCreatesTask() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskDto testTask = TestDataUtil.createTestTaskDtoA(savedProject.getId(), savedSection.getId());
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        String taskJson = objectMapper.writeValueAsString(testTask);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("TaskTestTitleA")
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long taskId = jsonNode.get("id").asLong();

        taskService.deleteTask(taskId);
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatUpdateTaskReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        TaskEntity savedTask = taskService.saveTask(testTask);
        savedTask.setTitle("Updated title");

        TaskDto updatedTask = taskMapper.mapToTaskDto(savedTask);

        String taskJson = objectMapper.writeValueAsString(updatedTask);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatUpdateTaskSuccessfullyUpdatesTask() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        TaskEntity savedTask = taskService.saveTask(testTask);
        savedTask.setTitle("Updated title");

        TaskDto updatedTask = taskMapper.mapToTaskDto(savedTask);

        String taskJson = objectMapper.writeValueAsString(updatedTask);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Updated title")
        );

        //Clean up
        taskService.deleteTask(savedTask.getId());
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatDeleteTaskUnsuccessfullReturnsHttp204() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/" + 999)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );

        //Clean up
    }

    @Test
    void testThatDeleteTaskReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject  = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        TaskEntity testTask = TestDataUtil.createTestTaskA(savedProject, savedSection);
        testTask.setUsers(List.of());
        testTask.setDependentTasks(List.of());
        TaskEntity savedTask = taskService.saveTask(testTask);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/" + savedTask.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        sectionService.deleteSection(savedSection.getId());
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }
}
