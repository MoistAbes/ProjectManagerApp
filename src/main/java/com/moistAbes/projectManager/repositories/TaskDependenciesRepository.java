package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import com.moistAbes.projectManager.domain.entity.TaskDependencyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskDependenciesRepository extends CrudRepository<TaskDependenciesEntity, Long> {


    @Override
    List<TaskDependenciesEntity> findAll();

    void deleteById(TaskDependencyId id);
}

