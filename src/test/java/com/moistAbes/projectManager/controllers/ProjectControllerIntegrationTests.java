package com.moistAbes.projectManager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.mappers.ProjectMapper;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import com.moistAbes.projectManager.services.impl.UserServiceImpl;
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

import java.io.UnsupportedEncodingException;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProjectControllerIntegrationTests {

    private MockMvc mockMvc;
    private ProjectServiceImpl projectService;
    private UserServiceImpl userService;
    private ObjectMapper objectMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectControllerIntegrationTests(MockMvc mockMvc, ProjectServiceImpl projectService, ObjectMapper objectMapper, UserServiceImpl userService, ProjectMapper projectMapper) {
        this.mockMvc = mockMvc;
        this.projectService = projectService;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.projectMapper = projectMapper;
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsHttp201Created() throws Exception {
        //Given
        UserEntity testUserA = TestDataUtil.createTestUserA();
        UserEntity savedUserEntity = userService.saveUser(testUserA);

        ProjectDto testProjectDtoA = TestDataUtil.createTestProjectDtoA();
        testProjectDtoA.setUsersId(List.of(savedUserEntity.getId()));

        String projectJson = objectMapper.writeValueAsString(testProjectDtoA);

        //When & Then
        MvcResult result = mockMvc.perform(
                 MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        //Clean up
        deleteProjectAndUser(result);
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsSavedProject() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity testSavedUser = userService.saveUser(testUser);

        ProjectDto testProjectDtoA = TestDataUtil.createTestProjectDtoA();
        testProjectDtoA.setUsersId(List.of(testSavedUser.getId()));
        String projectJson = objectMapper.writeValueAsString(testProjectDtoA);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Test title")

        ).andReturn();

        //Clean up
        deleteProjectAndUser(result);

    }

    @Test
    public void testThatGetProjectsSuccesfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetProjectSuccesfullyReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity testSavedUser = userService.saveUser(testUser);

        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        testProjectA.setUsers(List.of(testSavedUser));

        Long savedProjectId = projectService.saveProject(testProjectA).getId();

        //Then & When
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/projects/" + savedProjectId)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        //Clean up
        deleteProjectAndUser(result);
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsCorrectData() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        testProjectA.setUsers(List.of(savedUser));

        Long savedProjectId = projectService.saveProject(testProjectA).getId();

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects/" + savedProjectId)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("TestProjectA")

        );

        //Clean up
        projectService.deleteProject(savedProjectId);
        userService.deleteUser(savedUser.getId());
    }

    @Test
    public void testThatUpdateProjectSucesfullyReturnsHtpp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        testProjectA.setUsers(List.of(savedUser));

        ProjectEntity savedProjectA = projectService.saveProject(testProjectA);
        savedProjectA.setTitle("Updated title");

        ProjectDto projectDto = projectMapper.mapToProjectDto(savedProjectA);
        String projectJson = objectMapper.writeValueAsString(projectDto);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        //Clean up
        deleteProjectAndUser(result);
    }

    @Test
    public void testThatUpdateProjectSucesfullyReturnsCorrectData() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        testProjectA.setUsers(List.of(savedUser));

        ProjectEntity savedProjectA = projectService.saveProject(testProjectA);
        savedProjectA.setTitle("Updated title");

        ProjectDto projectDto = projectMapper.mapToProjectDto(savedProjectA);

        String projectJson = objectMapper.writeValueAsString(projectDto);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Updated title")
        ).andReturn();

        //Clean up
        deleteProjectAndUser(result);
    }

    @Test
    public void testThatDeleteProjectUnsuccesfullyReturns204() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/projects/" + 999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteProjectSuccesfullyReturns200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        testProjectA.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProjectA);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/projects/" + savedProject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        userService.deleteUser(savedUser.getId());
    }

    public void deleteProjectAndUser(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long projectId = jsonNode.get("id").asLong();

        JsonNode usersIdNode = jsonNode.get("usersId");
        Long usersId = usersIdNode.get(0).asLong();

        projectService.deleteProject(projectId);
        userService.deleteUser(usersId);
    }
}
