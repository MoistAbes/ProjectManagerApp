package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.mappers.impl.TaskMapper;
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
    private final TaskMapper taskMapper;

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        TaskEntity taskEntity = taskService.getTask(taskId);
        return ResponseEntity.ok(taskMapper.mapToDto(taskEntity));
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getTasks(){
        List<TaskEntity> taskEntities = taskService.getTasks();
        return ResponseEntity.ok(taskMapper.mapToDtoList(taskEntities));
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
        System.out.println("co nam tu przychodzi: " + taskDto.toString());
        TaskEntity savedTask = taskService.saveTask(taskMapper.mapToEntity(taskDto));
        return new ResponseEntity<>(taskMapper.mapToDto(savedTask), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto){
        if (!taskService.itExists(taskDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskEntity updatedTask = taskService.saveTask(taskMapper.mapToEntity(taskDto));
        return ResponseEntity.ok(taskMapper.mapToDto(updatedTask));
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
