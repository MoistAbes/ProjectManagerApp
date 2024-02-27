package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;

import java.util.List;

public interface SectionService {

    SectionEntity saveSection(SectionEntity section);

    List<SectionEntity> getSections();

    SectionEntity getSection(Long id) throws SectionNotFoundException;

    boolean itExists(Long id);

    void deleteSection(Long id);

}
