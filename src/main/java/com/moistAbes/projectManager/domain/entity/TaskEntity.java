package com.moistAbes.projectManager.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private String status;
    @Column(name = "priority")
    private String priority;
    @Column(name = "progress")
    private String progress;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "endDate")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users_tasks",
            joinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    @ToString.Exclude
    private List<UserEntity> users = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(priority, that.priority)) return false;
        if (!Objects.equals(progress, that.progress)) return false;
        if (!Objects.equals(startDate, that.startDate)) return false;
        return Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (progress != null ? progress.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}