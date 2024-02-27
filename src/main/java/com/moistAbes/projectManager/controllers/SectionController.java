package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.SectionDto;
import com.moistAbes.projectManager.domain.entity.SectionEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;
import com.moistAbes.projectManager.mappersv2.SectionMapper;
import com.moistAbes.projectManager.services.impl.SectionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sections")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SectionController {

    private final SectionServiceImpl sectionService;
    private final SectionMapper sectionMapper;

    @GetMapping
    public ResponseEntity<List<SectionDto>> getSections(){
        List<SectionEntity> sectionEntities = sectionService.getSections();
        return ResponseEntity.ok(sectionMapper.mapToSectionDtoList(sectionEntities));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<SectionDto>> getSectionsWithProjectId(@PathVariable Long projectId){
        List<SectionEntity> sectionEntities = sectionService.getSections().stream()
                .filter(sectionEntity -> Objects.equals(sectionEntity.getProject().getId(), projectId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(sectionMapper.mapToSectionDtoList(sectionEntities));
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionDto> getSection(@PathVariable Long sectionId) throws SectionNotFoundException {
        SectionEntity section = sectionService.getSection(sectionId);
        return ResponseEntity.ok(sectionMapper.mapToSectionDto(section));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionDto> createSection(@RequestBody SectionDto sectionDto){
        SectionEntity savedSection = sectionService.saveSection(sectionMapper.mapToSectionEntity(sectionDto));
        return new ResponseEntity<>(sectionMapper.mapToSectionDto(savedSection), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionDto> updateSection(@RequestBody SectionDto sectionDto){
        if (!sectionService.itExists(sectionDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SectionEntity updatedSection = sectionService.saveSection(sectionMapper.mapToSectionEntity(sectionDto));
        return ResponseEntity.ok(sectionMapper.mapToSectionDto(updatedSection));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long sectionId){
        if (!sectionService.itExists(sectionId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sectionService.deleteSection(sectionId);
        return ResponseEntity.ok().build();
    }



}
