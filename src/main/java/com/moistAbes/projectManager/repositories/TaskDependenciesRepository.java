package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.TaskDependenciesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskDependenciesRepository extends CrudRepository<TaskDependenciesEntity, Long> {


//    @Query
//    List<TaskDependenciesEntity> retrieveTaskDependencyWithTaskId(@Param("TASK") Long taskId);
//
//    @Query(nativeQuery = true)
//    List<TaskDependenciesEntity> complexQuery(Long taskId);

    @Override
    List<TaskDependenciesEntity> findAll();
}

