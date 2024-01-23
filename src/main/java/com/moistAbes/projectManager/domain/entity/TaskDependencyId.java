package com.moistAbes.projectManager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
public class TaskDependencyId implements Serializable {
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "dependent_task_id")
    private Long dependentTaskId;

    public TaskDependencyId(Long taskId, Long dependentTaskId) {
        this.taskId = taskId;
        this.dependentTaskId = dependentTaskId;
    }
}
