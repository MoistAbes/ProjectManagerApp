package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.SectionNotFoundException;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappers.TaskDependenciesMapper;
import com.moistAbes.projectManager.mappers.TaskMapper;
import com.moistAbes.projectManager.services.impl.TaskDependenciesServiceImpl;
import com.moistAbes.projectManager.services.impl.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {


    private final TaskServiceImpl taskService;
    private final TaskDependenciesServiceImpl taskDependenciesService;
    private final TaskDependenciesMapper taskDependenciesMapper;
    private final TaskMapper taskMapper;

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        TaskEntity taskEntity = taskService.getTask(taskId);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(taskEntity));
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getTasks(){
        List<TaskEntity> taskEntities = taskService.getTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(taskEntities));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getProjectTasks(@PathVariable Long projectId){
        List<TaskEntity> taskEntities = taskService.getTasksWithProjectId(projectId);
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(taskEntities));
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) throws UserNotFoundException, ProjectNotFoundException, SectionNotFoundException {
        TaskEntity savedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));
        return new ResponseEntity<>(taskMapper.mapToTaskDto(savedTask), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) throws ProjectNotFoundException, SectionNotFoundException {
        if (!taskService.itExists(taskDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaskEntity updatedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));
        return ResponseEntity.ok(taskMapper.mapToTaskDto(updatedTask));
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        if (!taskService.itExists(taskId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

}
