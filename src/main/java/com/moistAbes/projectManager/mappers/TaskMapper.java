package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.domain.dto.TaskDependenciesDto;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;
import com.moistAbes.projectManager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskDependenciesRepository taskDependenciesRepository;
    private final TaskRepository taskRepository;
    private final SectionRepository sectionRepository;

    private final TaskDependenciesMapper taskDependenciesMapper;

    public TaskEntity mapToTaskEntity(TaskDto taskDto) throws ProjectNotFoundException, SectionNotFoundException {


        return TaskEntity.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .content(taskDto.getContent())
                .priority(taskDto.getPriority())
                .progress(taskDto.getProgress())
                .startDate(taskDto.getStartDate())
                .endDate(taskDto.getEndDate())
                .project(projectRepository.findById(taskDto.getProjectId()).orElseThrow(ProjectNotFoundException::new))
                .users(taskDto.getUsers().stream()
                        .map(userId -> userRepository.findById(userId).orElseGet(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .dependentTasks(taskDto.getDependentTasks().stream()
                        .map(dependentTaskId -> taskDependenciesMapper.mapToTaskDependenciesEntity(new TaskDependenciesDto(taskDto.getId(), dependentTaskId)))
                        .collect(Collectors.toList()))
                .section(sectionRepository.findById(taskDto.getSectionId()).orElseThrow(SectionNotFoundException::new))
                .build();
    }


    public TaskDto mapToTaskDto(TaskEntity taskEntity){

        return TaskDto.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .content(taskEntity.getContent())
                .priority(taskEntity.getPriority())
                .progress(taskEntity.getProgress())
                .startDate(taskEntity.getStartDate())
                .endDate(taskEntity.getEndDate())
                .projectId(taskEntity.getProject().getId())
                .users(taskEntity.getUsers().stream()
                        .map(UserEntity::getId)
                        .collect(Collectors.toList())
                ).dependentTasks(taskEntity.getDependentTasks().stream()
                        .map(taskDependenciesEntity -> taskDependenciesEntity.getDependentTask().getId())
                        .collect(Collectors.toList()))
                .sectionId(taskEntity.getSection().getId())
                .build();
    }

    public List<TaskDto> mapToTaskDtoList(List<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }
}

