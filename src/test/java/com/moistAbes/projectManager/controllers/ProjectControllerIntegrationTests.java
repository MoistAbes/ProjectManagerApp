package com.moistAbes.projectManager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProjectControllerIntegrationTests {

    private MockMvc mockMvc;
    private ProjectServiceImpl projectService;
    private ObjectMapper objectMapper;

    @Autowired
    public ProjectControllerIntegrationTests(MockMvc mockMvc, ProjectServiceImpl projectService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsHttp201Created() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        String projectJson = objectMapper.writeValueAsString(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsSavedProject() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        String projectJson = objectMapper.writeValueAsString(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("TestProjectA")

        );
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
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        String projectJson = objectMapper.writeValueAsString(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateProjectSuccesfullyReturnsCorrectData() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        String projectJson = objectMapper.writeValueAsString(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("TestProjectA")

        );
    }

    @Test
    public void testThatUpdateProjectSucesfullyReturnsHtpp200() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);

        ProjectEntity savedProjectA = projectService.saveProject(testProjectA);
        savedProjectA.setTitle("Updated title");

        String projectJson = objectMapper.writeValueAsString(savedProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateProjectSucesfullyReturnsCorrectData() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);

        ProjectEntity savedProjectA = projectService.saveProject(testProjectA);
        savedProjectA.setTitle("Updated title");

        String projectJson = objectMapper.writeValueAsString(savedProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Updated title")
        );
    }

    @Test
    public void testThatDeleteProjectUnsuccesfullyReturns204() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        ProjectEntity savedProject = projectService.saveProject(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/projects/" + 999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteProjectSuccesfullyReturns200() throws Exception {
        List<TaskEntity> testTaskListA = TestDataUtil.createTestTaskListA();
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA(testTaskListA);
        ProjectEntity savedProject = projectService.saveProject(testProjectA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/projects/" + savedProject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
}
