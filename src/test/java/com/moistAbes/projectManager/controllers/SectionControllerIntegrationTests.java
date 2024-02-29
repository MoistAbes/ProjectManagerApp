package com.moistAbes.projectManager.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.SectionDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.mappers.SectionMapper;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import com.moistAbes.projectManager.services.impl.SectionServiceImpl;
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
import static org.hamcrest.Matchers.*;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class SectionControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private SectionMapper sectionMapper;
    private SectionServiceImpl sectionService;
    private UserServiceImpl userService;
    private ProjectServiceImpl projectService;

    @Autowired
    public SectionControllerIntegrationTests(MockMvc mockMvc, SectionServiceImpl sectionService, UserServiceImpl userService, ProjectServiceImpl projectService, ObjectMapper objectMapper, SectionMapper sectionMapper) {
        this.mockMvc = mockMvc;
        this.sectionService = sectionService;
        this.userService = userService;
        this.projectService = projectService;
        this.objectMapper = objectMapper;
        this.sectionMapper = sectionMapper;
    }

    @Test
    void testThatGetSectionsReturnsHttp200() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sections")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
    }

    @Test
    void testThatGetSectionsReturnsSections() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSectionA = TestDataUtil.createSectionA(savedProject);
        SectionEntity testSectionB = TestDataUtil.createSectionB(savedProject);
        SectionEntity testSectionC = TestDataUtil.createSectionC(savedProject);

        Long savedSectionAId = sectionService.saveSection(testSectionA).getId();
        Long savedSectionBId = sectionService.saveSection(testSectionB).getId();
        Long savedSectionCId = sectionService.saveSection(testSectionC).getId();

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sections")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].name", Matchers.allOf(hasItem("Test section A"), hasItem("Test section B"), hasItem("Test section C")))

        );

        //Clean up
        sectionService.deleteSection(savedSectionAId);
        sectionService.deleteSection(savedSectionBId);
        sectionService.deleteSection(savedSectionCId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetSectionReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSectionA = TestDataUtil.createSectionA(savedProject);
        Long savedSectionId = sectionService.saveSection(testSectionA).getId();

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sections/" + savedSectionId)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        sectionService.deleteSection(savedSectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetSectionReturnsSection() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSectionA = TestDataUtil.createSectionA(savedProject);
        Long savedSectionId = sectionService.saveSection(testSectionA).getId();

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/sections/" + savedSectionId)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test section A")
        ).andReturn();

        //Clean up
        sectionService.deleteSection(savedSectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetSectionsWithProjectIdReturnsHttps200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sections/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetSectionsWithProjectIdReturnsSections() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSectionA = TestDataUtil.createSectionA(savedProject);
        SectionEntity testSectionB = TestDataUtil.createSectionB(savedProject);
        SectionEntity testSectionC = TestDataUtil.createSectionC(savedProject);

        Long savedSectionAId = sectionService.saveSection(testSectionA).getId();
        Long savedSectionBId = sectionService.saveSection(testSectionB).getId();
        Long savedSectionCId = sectionService.saveSection(testSectionC).getId();

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/sections/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].name", Matchers.allOf(hasItem("Test section A"), hasItem("Test section B"), hasItem("Test section C")))
        );

        //Clean up
        sectionService.deleteSection(savedSectionAId);
        sectionService.deleteSection(savedSectionBId);
        sectionService.deleteSection(savedSectionCId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatCreateSectionReturnsHttp201() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionDto testSection = TestDataUtil.createDtoSectionA(savedProject.getId());

        String sectionJson = objectMapper.writeValueAsString(testSection);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sectionJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long sectionId = jsonNode.get("id").asLong();

        sectionService.deleteSection(sectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatCreateSectionCreatesSection() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionDto testSection = TestDataUtil.createDtoSectionA(savedProject.getId());

        String sectionJson = objectMapper.writeValueAsString(testSection);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sectionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test section A")
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long sectionId = jsonNode.get("id").asLong();

        sectionService.deleteSection(sectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatUpdateSectionReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);
        savedSection.setName("Updated name");
        SectionEntity updatedSection = sectionService.saveSection(savedSection);
        SectionDto updatedSectionDto = sectionMapper.mapToSectionDto(updatedSection);

        String projectJson = objectMapper.writeValueAsString(updatedSectionDto);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long sectionId = jsonNode.get("id").asLong();

        sectionService.deleteSection(sectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatUpdateSectionReturnsUpdatedSection() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);
        savedSection.setName("Updated name");
        SectionEntity updatedSection = sectionService.saveSection(savedSection);
        SectionDto updatedSectionDto = sectionMapper.mapToSectionDto(updatedSection);

        String projectJson = objectMapper.writeValueAsString(updatedSectionDto);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated name")
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);

        Long sectionId = jsonNode.get("id").asLong();

        sectionService.deleteSection(sectionId);
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatDeleteSectionUnsuccesfullReturnsHttp204() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sections/" + 999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    void testThatDeleteSectionReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUser));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        SectionEntity testSection = TestDataUtil.createSectionA(savedProject);
        SectionEntity savedSection = sectionService.saveSection(testSection);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sections/" + savedSection.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUser.getId());
    }
}
