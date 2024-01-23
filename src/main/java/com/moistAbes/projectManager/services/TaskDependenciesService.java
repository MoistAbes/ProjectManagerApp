package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.exceptions.TaskDependenciesNotFoundException;

import java.util.List;

public interface TaskDependenciesService{
    TaskDependenciesEntity saveTaskDependencies(TaskDependenciesEntity taskDependenciesEntity);

    List<TaskDependenciesEntity> getTaskDependenciesList();

    TaskDependenciesEntity getTaskDependencies(Long id) throws TaskDependenciesNotFoundException;

    boolean itExists(Long id);

    void deleteTaskDependencies(Long id);


}
