package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskDependencyId;
import com.moistAbes.projectManager.services.impl.TaskDependenciesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskDependenciesServiceImplTests {


    private final TaskDependenciesServiceImpl underTest;

    @Autowired
    public TaskDependenciesServiceImplTests(TaskDependenciesServiceImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatFindAllWorks(){

        //List<TaskDependenciesEntity> result = underTest.deleteTaskDependenciesByTask();


        //assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    public void testThatCanFindSpecificTaskDependency(){

//        List<TaskDependenciesEntity> result = underTest.deleteTaskDependenciesByTask();
//        TaskDependencyId resultId = null;
//
//        for (TaskDependenciesEntity taskDependency : result){
//            if (taskDependency.getTask().getId() == 4903){
//                System.out.println("TASK DEPENDENCY ID: " + taskDependency.getId());
//                resultId = taskDependency.getId();
//            }
//        }
//
//
//        assertThat(resultId).isNotNull();
    }

    @Test
    public void testThatCanDeleteById(){

//        List<TaskDependenciesEntity> result = underTest.deleteTaskDependenciesByTask();
//        TaskDependencyId resultId = null;
//
//        for (TaskDependenciesEntity taskDependency : result){
//            if (taskDependency.getTask().getId() == 4903){
//                System.out.println("TASK DEPENDENCY ID: " + taskDependency.getId());
//                resultId = taskDependency.getId();
//            }
//        }
//
//        underTest.deleteTaskDependencies(resultId);
//
//
//        assertThat(underTest.deleteTaskDependenciesByTask()).isEmpty();
    }
}
