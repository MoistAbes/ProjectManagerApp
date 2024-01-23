package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskDependenciesRepositoryTests {

    private TaskDependenciesRepository underTest;
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public TaskDependenciesRepositoryTests(TaskDependenciesRepository underTest, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.underTest = underTest;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Test
    public void testThatNamedQueryWorks() throws TaskNotFoundException {

//      TaskEntity task = taskRepository.findById(4503L).orElseThrow(TaskNotFoundException::new);
//
//
//      List<TaskDependenciesEntity> resultList = underTest.retrieveTaskDependencyWithTaskId(task);
//
//      assertThat(resultList.size()).isEqualTo(9);

    }
}
