package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappersv2.TaskDependenciesMapper2;
import com.moistAbes.projectManager.mappersv2.TaskMapper2;
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
    private final TaskDependenciesMapper2 taskDependenciesMapper;
    private final TaskMapper2 taskMapper;

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        TaskEntity taskEntity = taskService.getTask(taskId);
//        List<TaskDependenciesEntity> taskDependenciesEntities = taskDependenciesService.getTaskDependenciesList();
//
//        List<TaskDependenciesEntity> resultList = taskDependenciesEntities.stream()
//                .filter(taskDependenciesEntity -> taskDependenciesEntity.getTask().getId().equals(taskEntity.getId()))
//                .collect(Collectors.toList());
//
//        taskEntity.setDependentTasks(resultList);
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
        TaskEntity savedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));
        return new ResponseEntity<>(taskMapper.mapToTaskDto(savedTask), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) throws UserNotFoundException, ProjectNotFoundException {
        if (!taskService.itExists(taskDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        System.out.println("Task dependencies taskdto: " + taskDto.getDependentTasks().size());
//        List<Long> taskIdList = taskDependenciesService.getTaskDependenciesList().stream()
//                .map(TaskDependenciesEntity::getDependentTask)
//                .map(TaskEntity::getId)
//                .collect(Collectors.toList());
//        List<Long> taskDependencyIdList = taskDependenciesService.getTaskDependenciesList().stream()
//                .map(TaskDependenciesEntity::getId)
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < taskIdList.size(); i++){
//            System.out.println("task: " + taskIdList.get(i).toString());
//            if (!taskDto.getDependentTasks().contains(taskIdList.get(i))){
//                System.out.println("dzieje sie to: " + taskIdList.get(i));
//                taskDependenciesService.deleteTaskDependencies(taskDependencyIdList.get(i));
//            }
//        }

        TaskEntity updatedTask = taskService.saveTask(taskMapper.mapToTaskEntity(taskDto));

        System.out.println("Task dependencies saved task: " + updatedTask.getDependentTasks().size());

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
