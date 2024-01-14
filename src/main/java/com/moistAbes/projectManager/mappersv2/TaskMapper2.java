package com.moistAbes.projectManager.mappersv2;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.repositories.ProjectRepository;
import com.moistAbes.projectManager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskMapper2 {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public TaskEntity mapToTaskEntity(TaskDto taskDto) throws ProjectNotFoundException, UserNotFoundException {


            return TaskEntity.builder()
                    .id(taskDto.getId())
                    .title(taskDto.getTitle())
                    .content(taskDto.getContent())
                    .status(taskDto.getStatus())
                    .priority(taskDto.getPriority())
                    .progress(taskDto.getProgress())
                    .startDate(taskDto.getStartDate())
                    .endDate(taskDto.getEndDate())
                    .project(projectRepository.findById(taskDto.getProjectId()).orElseThrow(ProjectNotFoundException::new))
                    .users(taskDto.getUsers().stream()
                            .map(userId -> userRepository.findById(userId).orElseGet(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
                    ).build();
    }


    public TaskDto mapToTaskDto(TaskEntity taskEntity){

        return TaskDto.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .content(taskEntity.getContent())
                .status(taskEntity.getStatus())
                .priority(taskEntity.getPriority())
                .progress(taskEntity.getProgress())
                .startDate(taskEntity.getStartDate())
                .endDate(taskEntity.getEndDate())
                .projectId(taskEntity.getProject().getId())
                .users(taskEntity.getUsers().stream()
                        .map(UserEntity::getId)
                        .collect(Collectors.toList())
                ).build();
    }

    public List<TaskDto> mapToTaskDtoList(List<TaskEntity> taskEntities){
        return taskEntities.stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }
}

