package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.domain.entity.SectionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface SectionRepository extends CrudRepository<SectionEntity, Long> {

    @Override
    List<SectionEntity> findAll();
}
