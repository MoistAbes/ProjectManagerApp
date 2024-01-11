package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.mappers.impl.ProjectMapper;
import com.moistAbes.projectManager.mappersv2.ProjectMapper2;
import com.moistAbes.projectManager.services.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {

    private final ProjectServiceImpl projectService;
    private final ProjectMapper2 projectMapper;


    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long projectId) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectService.getProject(projectId);
        return ResponseEntity.ok(projectMapper.mapToProjectDto(projectEntity));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(){
        List<ProjectEntity> projects = projectService.getProjects();
        return ResponseEntity.ok(projectMapper.mapToProjectDtoList(projects));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto){
        ProjectEntity projectEntity = projectMapper.mapToProjectEntity(projectDto);
        ProjectEntity savedProjectEntity = projectService.saveProject(projectEntity);
        return new ResponseEntity<>(projectMapper.mapToProjectDto(savedProjectEntity), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto){
        if (!projectService.itExists(projectDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProjectEntity project = projectMapper.mapToProjectEntity(projectDto);
        ProjectEntity updatedProject = projectService.saveProject(project);
        return ResponseEntity.ok(projectMapper.mapToProjectDto(updatedProject));
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId){
        if (!projectService.itExists(projectId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.deleteProject(projectId);
        return ResponseEntity.ok().build();
    }



}
