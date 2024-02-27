package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    @Override
    List<TaskEntity> findAll();

    List<TaskEntity> findByProjectId(Long projectId);


}
