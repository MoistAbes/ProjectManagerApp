package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskDependenciesNotFoundException;
import com.moistAbes.projectManager.repositories.TaskDependenciesRepository;
import com.moistAbes.projectManager.services.TaskDependenciesService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteTaskDependencies(Long id) {
        repository.deleteById(id);
    }
}
