package com.moistAbes.projectManager;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.springframework.scheduling.config.Task;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TestDataUtil {

    public static ProjectEntity createTestProjectA(List<TaskEntity> testTaskList){
        return ProjectEntity.builder()
                .title("TestProjectA")
                .tasks(testTaskList)
                .build();
    }
    public static ProjectEntity createTestProjectB(List<TaskEntity> testTaskList) {
        return ProjectEntity.builder()
                .title("TestProjectB")
                .tasks(testTaskList)
                .build();
    }
    public static ProjectEntity createTestProjectC(List<TaskEntity> testTaskList) {
        return ProjectEntity.builder()
                .title("TestProjectC")
                .tasks(testTaskList)
                .build();
    }

    public static TaskEntity createTestTaskA(){
        return TaskEntity.builder()
                .title("TaskTitleA")
                .content("TaskContentA")
                .priority("TaskPriorityA")
                .status("TestStatusA")
                .progress("TaskProgressA")
                .startDate(LocalDate.of(1987, 5, 3))
                .endDate(LocalDate.of(1987, 5, 4))
                .build();
    }

    public static TaskEntity createTestTaskB(){
        return TaskEntity.builder()
                .title("TaskTitleB")
                .content("TaskContentB")
                .priority("TaskPriorityB")
                .status("TestStatusB")
                .progress("TaskProgressB")
                .startDate(LocalDate.of(2001, 2, 12))
                .endDate(LocalDate.of(2003, 2, 25))
                .build();
    }

    public static TaskEntity createTestTaskC(){
        return TaskEntity.builder()
                .title("TaskTitleC")
                .content("TaskContentC")
                .priority("TaskPriorityC")
                .status("TestStatusC")
                .progress("TaskProgressC")
                .startDate(LocalDate.of(1995, 11, 5))
                .endDate(LocalDate.of(1995, 12, 16))
                .build();
    }

    public static List<TaskEntity> createTestTaskListA(){
        return List.of(
                createTestTaskA(),
                createTestTaskB(),
                createTestTaskC()
        );
    }
}