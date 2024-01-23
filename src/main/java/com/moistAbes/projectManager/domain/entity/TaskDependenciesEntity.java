package com.moistAbes.projectManager.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@NamedQuery(
        name = "TaskDependenciesEntity.retrieveTaskDependencyWithTaskId",
        query = "FROM TaskDependenciesEntity WHERE task > :TASK"
)
@NamedNativeQuery(
        name="TaskDependenciesEntity.complexQuery",
        query="select * from task_dependencies where task_id = ?1",
        resultClass=TaskDependenciesEntity.class
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_dependencies")
public class TaskDependenciesEntity {

    @EmbeddedId
    private TaskDependencyId id;

    @ManyToOne
    @JoinColumn(name = "task_id", updatable = false, insertable = false)
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "dependent_task_id", updatable = false, insertable = false)
    private TaskEntity dependentTask;

    public TaskDependenciesEntity(TaskEntity task, TaskEntity dependentTask) {
        this.task = task;
        this.dependentTask = dependentTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDependenciesEntity that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(task, that.task)) return false;
        return Objects.equals(dependentTask, that.dependentTask);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (dependentTask != null ? dependentTask.hashCode() : 0);
        return result;
    }
}
