package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.mappers.impl.ProjectMapper;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {

    private final ProjectServiceImpl projectService;
    private final ProjectMapper projectMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto){
        ProjectEntity projectEntity = projectMapper.mapToEntity(projectDto);
        ProjectEntity savedProjectEntity = projectService.saveProject(projectEntity);
        return new ResponseEntity<>(projectMapper.mapToDto(savedProjectEntity), HttpStatus.CREATED);

    }



}
