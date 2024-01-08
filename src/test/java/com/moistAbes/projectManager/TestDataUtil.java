package com.moistAbes.projectManager;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.springframework.scheduling.config.Task;

import javax.xml.crypto.Data;
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

    public static List<TaskEntity> createTestTaskListA(){

        return List.of(
                TaskEntity.builder()
                        .title("TestTaskTitleA")
                        .content("TestTaskContentA")
                        .status("TestTaskStatusA")
                        .priority("TestTaskPriorityA")
                        .progress("TestTaskProgressA")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()

                ,TaskEntity.builder()
                        .title("TestTaskTitleB")
                        .content("TestTaskContentB")
                        .status("TestTaskStatusB")
                        .priority("TestTaskPriorityB")
                        .progress("TestTaskProgressB")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()

                ,TaskEntity.builder()
                        .title("TestTaskTitleC")
                        .content("TestTaskContentC")
                        .status("TestTaskStatusC")
                        .priority("TestTaskPriorityC")
                        .progress("TestTaskProgressC")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()
        );
    }
}