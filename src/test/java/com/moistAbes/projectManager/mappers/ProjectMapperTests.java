package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.mappers.impl.ProjectMapper;
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

    @Autowired
    public ProjectMapperTests(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Test
    public void testThatMapperCorrectlyMapsFromDtoToEntity(){
        //given
        ProjectDto testProjectDtoA = TestDataUtil.createTestProjectDtoA();
        List<TaskDto> testTaskDtoList = TestDataUtil.createTestTaskDtoListA();
        testProjectDtoA.setTasks(testTaskDtoList);

        //when
        ProjectEntity mappedProject = projectMapper.mapToEntity(testProjectDtoA);

        //then
        assertThat(mappedProject.getId()).isEqualTo(testProjectDtoA.getId());
        assertThat(mappedProject.getTitle()).isEqualTo(testProjectDtoA.getTitle());
        assertThat(mappedProject.getTasks().size()).isEqualTo(testProjectDtoA.getTasks().size());
        mappedProject.getTasks()
                .forEach(System.out::println);
    }

    @Test
    public void testThatMapperCorrectlyMapsFromEntityToDto(){
        //given
        ProjectEntity testProjectA = TestDataUtil.createTestProjectA();
        List<TaskEntity> taskEntities = TestDataUtil.createTestTaskListA(testProjectA);
        testProjectA.setTasks(taskEntities);

        testProjectA.getTasks()
                .forEach(System.out::println);

        System.out.println("");

        //when
        ProjectDto mappedProject = projectMapper.mapToDto(testProjectA);

        //then
        assertThat(mappedProject.getId()).isEqualTo(testProjectA.getId());
        assertThat(mappedProject.getTitle()).isEqualTo(testProjectA.getTitle());
        assertThat(mappedProject.getTasks().size()).isEqualTo(testProjectA.getTasks().size());
        assertThat(mappedProject.getTasks().get(0).getProjectId()).isEqualTo(testProjectA.getTasks().get(0).getProject().getId());
        mappedProject.getTasks()
                .forEach(System.out::println);
    }
}
