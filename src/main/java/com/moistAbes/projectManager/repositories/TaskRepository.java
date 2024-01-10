package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    @Override
    List<TaskEntity> findAll();
}
