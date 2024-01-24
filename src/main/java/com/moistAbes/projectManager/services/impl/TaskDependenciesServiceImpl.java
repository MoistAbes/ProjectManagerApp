package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskDependencyId;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskDependenciesNotFoundException;
import com.moistAbes.projectManager.repositories.TaskDependenciesRepository;
import com.moistAbes.projectManager.services.TaskDependenciesService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskDependenciesServiceImpl implements TaskDependenciesService {

    private TaskDependenciesRepository repository;

    public TaskDependenciesServiceImpl(TaskDependenciesRepository repository) {
        this.repository = repository;
    }


    @Override
    public TaskDependenciesEntity saveTaskDependencies(TaskDependenciesEntity taskDependenciesEntity) {
        return repository.save(taskDependenciesEntity);
    }

    @Override
    public List<TaskDependenciesEntity> getTaskDependenciesList() {
        return repository.findAll();
    }

    @Override
    public TaskDependenciesEntity getTaskDependencies(Long id) throws TaskDependenciesNotFoundException {
        return repository.findById(id).orElseThrow(TaskDependenciesNotFoundException::new);
    }

    @Override
    public boolean itExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteTaskDependencies(TaskDependencyId id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteTaskDependenciesByTaskId(Long taskId) {
        List<TaskDependenciesEntity> result = repository.findAll();

        TaskDependencyId resultId = null;

        for (TaskDependenciesEntity taskDependency : result){
            System.out.println("TASK DEPENDENCY ID: " + taskDependency.getId());
            System.out.println("task id: " + taskDependency.getDependentTask().getId() + " id: " + taskId);
            if (Objects.equals(taskDependency.getDependentTask().getId(), taskId)){
                System.out.println("JESTESMY W IFIE");
                resultId = taskDependency.getId();
            }
        }

        repository.deleteById(resultId);
    }
}
