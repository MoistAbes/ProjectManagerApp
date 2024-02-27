package com.moistAbes.projectManager.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(
            targetEntity = TaskEntity.class,
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<TaskEntity> tasks = new ArrayList<>();

    @OneToMany(
            targetEntity = SectionEntity.class,
            mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<SectionEntity> sections = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users_projects",
            joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")}
    )
    private List<UserEntity> users = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) return false;

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        ProjectEntity projectEntity = (ProjectEntity) o;

        // typecast o to Complex so that we can compare data members

        // Compare the data members and return accordingly
        return
                id.equals(projectEntity.id) &&
                        title.equals(projectEntity.title);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}