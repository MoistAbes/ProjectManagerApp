package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectEntity saveProject(ProjectEntity projectEntity);

    List<ProjectEntity> getProjects();

    Optional<ProjectEntity> getProject(Long id);

    boolean itExists(Long id);

    void deleteProject(Long id);

}
