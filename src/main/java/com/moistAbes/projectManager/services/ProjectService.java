package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import java.util.List;

public interface ProjectService {
    ProjectEntity saveProject(ProjectEntity projectEntity);

    List<ProjectEntity> getProjects();

    ProjectEntity getProject(Long id) throws ProjectNotFoundException;

    boolean itExists(Long id);

    void deleteProject(Long id);

}
