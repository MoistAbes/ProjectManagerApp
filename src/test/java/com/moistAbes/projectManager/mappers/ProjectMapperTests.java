package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
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
public class ProjectMapperTests {

    private final ProjectMapper projectMapper;
    private final UserServiceImpl userService;
    private final ProjectServiceImpl projectService;

    @Autowired
    public ProjectMapperTests(ProjectMapper projectMapper, UserServiceImpl userService, ProjectServiceImpl projectService) {
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Test
    public void testThatMapperCorrectlyMapsFromDtoToEntity(){
        //Given
        Long savedUserAId = userService.saveUser(TestDataUtil.createTestUserA()).getId();
        Long savedUserBId = userService.saveUser(TestDataUtil.createTestUserB()).getId();
        Long savedUserCId = userService.saveUser(TestDataUtil.createTestUserC()).getId();

        ProjectDto testProjectDto = TestDataUtil.createTestProjectDtoA();
        testProjectDto.setUsersId(List.of(savedUserAId, savedUserBId, savedUserCId));

        //When
        ProjectEntity mappedProject = projectMapper.mapToProjectEntity(testProjectDto);

        //Then
        assertThat(mappedProject.getId()).isEqualTo(testProjectDto.getId());
        assertThat(mappedProject.getTitle()).isEqualTo(testProjectDto.getTitle());
        assertThat(mappedProject.getUsers().size()).isEqualTo(testProjectDto.getUsersId().size());
        assertThat(mappedProject.getUsers().get(0).getId()).isEqualTo(testProjectDto.getUsersId().get(0));
        assertThat(mappedProject.getUsers().get(1).getId()).isEqualTo(testProjectDto.getUsersId().get(1));
        assertThat(mappedProject.getUsers().get(2).getId()).isEqualTo(testProjectDto.getUsersId().get(2));

        //Clean up
        userService.deleteUser(savedUserAId);
        userService.deleteUser(savedUserBId);
        userService.deleteUser(savedUserCId);
    }

    @Test
    public void testThatMapperCorrectlyMapsFromEntityToDto(){
        //Given
        UserEntity savedUserA = userService.saveUser(TestDataUtil.createTestUserA());
        UserEntity savedUserB = userService.saveUser(TestDataUtil.createTestUserB());
        UserEntity savedUserC = userService.saveUser(TestDataUtil.createTestUserC());

        ProjectEntity testProject = TestDataUtil.createTestProjectA();
        testProject.setUsers(List.of(savedUserA, savedUserB, savedUserC));
        ProjectEntity savedProject = projectService.saveProject(testProject);

        //When
        ProjectDto mappedProject = projectMapper.mapToProjectDto(savedProject);

        //Then
        assertThat(mappedProject.getId()).isEqualTo(savedProject.getId());
        assertThat(mappedProject.getTitle()).isEqualTo(savedProject.getTitle());
        assertThat(mappedProject.getUsersId().size()).isEqualTo(savedProject.getUsers().size());
        assertThat(mappedProject.getUsersId().get(0)).isEqualTo(savedProject.getUsers().get(0).getId());
        assertThat(mappedProject.getUsersId().get(1)).isEqualTo(savedProject.getUsers().get(1).getId());
        assertThat(mappedProject.getUsersId().get(2)).isEqualTo(savedProject.getUsers().get(2).getId());
    }
}
