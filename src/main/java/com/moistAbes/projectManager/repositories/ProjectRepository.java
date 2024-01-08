package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    @Override
    List<ProjectEntity> findAll();
}