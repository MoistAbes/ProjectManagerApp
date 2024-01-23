package com.moistAbes.projectManager.mappersv2;

import com.moistAbes.projectManager.domain.dto.TaskDependenciesDto;
import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskDependencyId;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.repositories.TaskDependenciesRepository;
import com.moistAbes.projectManager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskDependenciesMapper2 {

    private final TaskDependenciesRepository taskDependenciesRepository;
    private final TaskRepository taskRepository;



    public TaskDependenciesEntity mapToTaskDependenciesEntity(TaskDependenciesDto taskDependenciesDto) {

        return TaskDependenciesEntity.builder()
                .id(new TaskDependencyId(taskDependenciesDto.getTaskId(), taskDependenciesDto.getDependentTaskId()))
                .task(taskRepository.findById(taskDependenciesDto.getTaskId()).orElseGet(null))
                .dependentTask(taskRepository.findById(taskDependenciesDto.getDependentTaskId()).orElseGet(null))
                .build();
    }

    public TaskDependenciesDto mapToTaskDependenciesDto(TaskDependenciesEntity taskDependencies){
        return TaskDependenciesDto.builder()
                .taskId(taskDependencies.getTask().getId())
                .dependentTaskId(taskDependencies.getDependentTask().getId())
                .build();
    }

    public List<TaskDependenciesDto> mapToTaskDependenciesDtoList(List<TaskDependenciesEntity> taskDependenciesEntities){

        return taskDependenciesEntities.stream()
                .map(this::mapToTaskDependenciesDto)
                .collect(Collectors.toList());
    }

}
