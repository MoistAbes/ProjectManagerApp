package com.moistAbes.projectManager;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.springframework.scheduling.config.Task;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TestDataUtil {

    public static ProjectEntity createTestProjectA(){
        return ProjectEntity.builder()
                .id(1L)
                .title("TestProjectA")
                .build();
    }
    public static ProjectEntity createTestProjectB() {
        return ProjectEntity.builder()
                .id(2L)
                .title("TestProjectB")
                .build();
    }
    public static ProjectEntity createTestProjectC() {
        return ProjectEntity.builder()
                .id(3L)
                .title("TestProjectC")
                .build();
    }

    public static ProjectDto createTestProjectDtoA(){
        return ProjectDto.builder()
                .id(123L)
                .title("Test title")
                .build();
    }

    public static TaskDto createTaskDtoA(){
        return TaskDto.builder()
                .title("TaskTitleA")
                .content("TaskContentA")
                .priority("TaskPriorityA")
                .status("TestStatusA")
                .progress("TaskProgressA")
                .startDate(LocalDate.of(1987, 5, 3))
                .endDate(LocalDate.of(1987, 5, 4))
                .projectId(123L)
                .build();
    }

    public static TaskDto createTaskDtoB(){
        return TaskDto.builder()
                .title("TaskTitleB")
                .content("TaskContentB")
                .priority("TaskPriorityB")
                .status("TestStatusB")
                .progress("TaskProgressB")
                .startDate(LocalDate.of(1987, 5, 3))
                .endDate(LocalDate.of(1987, 5, 4))
                .projectId(123L)
                .build();
    }

    public static TaskDto createTaskDtoC(){
        return TaskDto.builder()
                .title("TaskTitleC")
                .content("TaskContentC")
                .priority("TaskPriorityC")
                .status("TestStatusC")
                .progress("TaskProgressC")
                .startDate(LocalDate.of(1987, 5, 3))
                .endDate(LocalDate.of(1987, 5, 4))
                .projectId(123L)
                .build();
    }

    public static List<TaskDto> createTestTaskDtoListA(){
        return List.of(
                createTaskDtoA(),
                createTaskDtoB(),
                createTaskDtoC()
        );
    }

    public static TaskEntity createTestTaskA(ProjectEntity projectEntity){
        return TaskEntity.builder()
                .title("TaskTitleA")
                .content("TaskContentA")
                .priority("TaskPriorityA")
                .status("TestStatusA")
                .progress("TaskProgressA")
                .startDate(LocalDate.of(1987, 5, 3))
                .endDate(LocalDate.of(1987, 5, 4))
                .project(projectEntity)
                .build();
    }

    public static TaskEntity createTestTaskB(ProjectEntity projectEntity){
        return TaskEntity.builder()
                .title("TaskTitleB")
                .content("TaskContentB")
                .priority("TaskPriorityB")
                .status("TestStatusB")
                .progress("TaskProgressB")
                .startDate(LocalDate.of(2001, 2, 12))
                .endDate(LocalDate.of(2003, 2, 25))
                .project(projectEntity)
                .build();
    }

    public static TaskEntity createTestTaskC(ProjectEntity projectEntity){
        return TaskEntity.builder()
                .title("TaskTitleC")
                .content("TaskContentC")
                .priority("TaskPriorityC")
                .status("TestStatusC")
                .progress("TaskProgressC")
                .startDate(LocalDate.of(1995, 11, 5))
                .endDate(LocalDate.of(1995, 12, 16))
                .project(projectEntity)
                .build();
    }

    public static List<TaskEntity> createTestTaskListA(ProjectEntity projectEntity){
        return List.of(
                createTestTaskA(projectEntity),
                createTestTaskB(projectEntity),
                createTestTaskC(projectEntity)
        );
    }
}