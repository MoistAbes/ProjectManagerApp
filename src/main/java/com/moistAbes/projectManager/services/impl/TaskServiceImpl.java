package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.repositories.TaskDependenciesRepository;
import com.moistAbes.projectManager.repositories.TaskRepository;
import com.moistAbes.projectManager.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskDependenciesServiceImpl taskDependenciesService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskDependenciesServiceImpl taskDependenciesService) {
        this.taskRepository = taskRepository;
        this.taskDependenciesService = taskDependenciesService;
    }

    @Override
    public TaskEntity saveTask(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<TaskEntity> getTasksWithProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public TaskEntity getTask(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public boolean itExists(Long id) {
        return taskRepository.existsById(id);
    }

    @Override
    public void deleteTask(Long id) {
        taskDependenciesService.deleteTaskDependenciesByTaskId(id);
        taskRepository.deleteById(id);
    }
}
