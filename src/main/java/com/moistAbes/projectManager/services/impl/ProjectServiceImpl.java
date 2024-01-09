package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.repositories.ProjectRepository;
import com.moistAbes.projectManager.services.ProjectService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectEntity saveProject(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);
    }

    @Override
    public List<ProjectEntity> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity getProject(Long id) throws ProjectNotFoundException {
        return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public boolean itExists(Long id) {
        return projectRepository.existsById(id);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
