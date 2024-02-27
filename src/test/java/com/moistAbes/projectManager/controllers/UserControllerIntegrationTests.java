package com.moistAbes.projectManager.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.mappersv2.UserMapper2;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
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
public class UserControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserMapper2 userMapper;
    private UserServiceImpl userService;
    private ProjectServiceImpl projectService;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, UserMapper2 userMapper, UserServiceImpl userService, ProjectServiceImpl projectService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Test
    void testThatGetUsersReturnsHttp200() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
    }

    @Test
    void testThatGetUsersReturnsUsers() throws Exception {
        //Given
        UserEntity testUserA = TestDataUtil.createTestUserA();
        UserEntity testUserB = TestDataUtil.createTestUserB();
        UserEntity testUserC = TestDataUtil.createTestUserC();

        UserEntity savedUserA = userService.saveUser(testUserA);
        UserEntity savedUserB = userService.saveUser(testUserB);
        UserEntity savedUserC = userService.saveUser(testUserC);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].name", Matchers.allOf(hasItem("Test user name A"), hasItem("Test user name B"), hasItem("Test user name C")))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].surname", Matchers.allOf(hasItem("Test user surname A"), hasItem("Test user surname B"), hasItem("Test user surname C")))
        );

        //Clean up
        userService.deleteUser(savedUserA.getId());
        userService.deleteUser(savedUserB.getId());
        userService.deleteUser(savedUserC.getId());
    }

    @Test
    void testThatGetUserReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetUserReturnsUser() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.saveUser(testUser);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + savedUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test user name A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.surname").value("Test user surname A")
        );

        //Clean up
        userService.deleteUser(savedUser.getId());
    }

    @Test
    void testThatGetTasksWithProjectIdReturnsHttp200() throws Exception {
        //Given
        UserEntity testUserA = TestDataUtil.createTestUserA();
        UserEntity testUserB = TestDataUtil.createTestUserB();
        UserEntity testUserC = TestDataUtil.createTestUserC();

        UserEntity savedUserA = userService.saveUser(testUserA);
        UserEntity savedUserB = userService.saveUser(testUserB);
        UserEntity savedUserC = userService.saveUser(testUserC);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUserA, savedUserB, savedUserC));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        //Clean up
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUserA.getId());
        userService.deleteUser(savedUserB.getId());
        userService.deleteUser(savedUserC.getId());
    }

    @Test
    void testThatGetTasksWithProjectIdReturnsProjects() throws Exception {
        //Given
        UserEntity testUserA = TestDataUtil.createTestUserA();
        UserEntity testUserB = TestDataUtil.createTestUserB();
        UserEntity testUserC = TestDataUtil.createTestUserC();

        UserEntity savedUserA = userService.saveUser(testUserA);
        UserEntity savedUserB = userService.saveUser(testUserB);
        UserEntity savedUserC = userService.saveUser(testUserC);

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUserA, savedUserB, savedUserC));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        //When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/project/" + savedProject.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].name", Matchers.allOf(hasItem("Test user name A"), hasItem("Test user name B"), hasItem("Test user name C")))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[*].surname", Matchers.allOf(hasItem("Test user surname A"), hasItem("Test user surname B"), hasItem("Test user surname C")))
        );

        //Clean up
        projectService.deleteProject(savedProject.getId());
        userService.deleteUser(savedUserA.getId());
        userService.deleteUser(savedUserB.getId());
        userService.deleteUser(savedUserC.getId());
    }

    @Test
    void testThatCreateUserReturnsHttp201() throws Exception {
        //Given
        UserDto testUser = TestDataUtil.createTestUserDtoA();
        String userJson = objectMapper.writeValueAsString(testUser);


        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        Long userId = jsonNode.get("id").asLong();

        userService.deleteUser(userId);
    }

    @Test
    void testThatCreateUserCreatesUser() throws Exception {
        //Given
        UserDto testUser = TestDataUtil.createTestUserDtoA();
        String userJson = objectMapper.writeValueAsString(testUser);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Test user name A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.surname").value("Test user surname A")
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        Long userId = jsonNode.get("id").asLong();

        userService.deleteUser(userId);
    }

    @Test
    void testThatUpdateUserReturnsHttp200() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity saveUser = userService.saveUser(testUser);
        saveUser.setName("Updated name");
        saveUser.setName("Updated surname");
        UserDto updatedUser = userMapper.mapToUserDto(saveUser);
        String userJson = objectMapper.writeValueAsString(updatedUser);


        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        Long userId = jsonNode.get("id").asLong();

        userService.deleteUser(userId);
    }

    @Test
    void testThatUpdateUserUpdatesUser() throws Exception {
        //Given
        UserEntity testUser = TestDataUtil.createTestUserA();
        UserEntity saveUser = userService.saveUser(testUser);
        saveUser.setName("Updated name");
        saveUser.setSurname("Updated surname");
        UserDto updatedUser = userMapper.mapToUserDto(saveUser);
        String userJson = objectMapper.writeValueAsString(updatedUser);

        //When & Then
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated name")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.surname").value("Updated surname")
        ).andReturn();

        //Clean up
        String jsonResult = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResult);
        Long userId = jsonNode.get("id").asLong();

        userService.deleteUser(userId);
    }
}
