package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    TaskEntity saveTask(TaskEntity taskEntity);

    List<TaskEntity> getTasks();
    List<TaskEntity> getTasksWithProjectId(Long projectId);


    TaskEntity getTask(Long id) throws TaskNotFoundException;

    boolean itExists(Long id);

    void deleteTask(Long id);


}
