package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappers.impl.TaskMapper;
import com.moistAbes.projectManager.mappersv2.TaskMapper2;
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
    private final TaskMapper2 taskMapper;

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
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) throws UserNotFoundException, ProjectNotFoundException {
        System.out.println("co nam tu przychodzi: " + taskDto.toString());
        System.out.println("NA CO MAPUJE: " + taskMapper.mapToTaskEntity(taskDto));
        TaskEntity savedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));
        return new ResponseEntity<>(taskMapper.mapToTaskDto(savedTask), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) throws UserNotFoundException, ProjectNotFoundException {
        if (!taskService.itExists(taskDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("TASK DTO: " + taskDto);

        TaskEntity updatedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));
        System.out.println("TASK ENTITY: " + updatedTask);

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
