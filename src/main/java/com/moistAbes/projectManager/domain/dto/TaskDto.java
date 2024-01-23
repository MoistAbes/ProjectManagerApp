package com.moistAbes.projectManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    private Long id;
    private String title;
    private String content;
    private String status;
    private String priority;
    private String progress;
    private Long projectId;
    private List<Long> users;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> dependentTasks;
}
