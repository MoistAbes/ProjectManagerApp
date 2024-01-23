package com.moistAbes.projectManager.domain.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDependenciesDto {

    private Long id;
    private Long taskId;
    private Long dependentTaskId;

    public TaskDependenciesDto(Long taskId, Long dependentTaskId) {
        this.taskId = taskId;
        this.dependentTaskId = dependentTaskId;
    }
}
