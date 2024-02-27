package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;
import com.moistAbes.projectManager.repositories.SectionRepository;
import com.moistAbes.projectManager.services.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    public SectionServiceImpl(SectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public SectionEntity saveSection(SectionEntity section) {
        return repository.save(section);
    }

    @Override
    public List<SectionEntity> getSections() {
        return repository.findAll();
    }

    @Override
    public SectionEntity getSection(Long id) throws SectionNotFoundException {
        return repository.findById(id).orElseThrow(SectionNotFoundException::new);
    }

    @Override
    public boolean itExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteSection(Long id) {
        repository.deleteById(id);
    }
}
