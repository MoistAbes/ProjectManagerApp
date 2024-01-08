package com.moistAbes.projectManager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private ProjectDto project;
    private LocalDate startDate;
    private LocalDate endDate;

}
