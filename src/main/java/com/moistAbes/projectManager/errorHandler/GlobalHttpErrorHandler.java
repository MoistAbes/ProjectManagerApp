package com.moistAbes.projectManager.errorHandler;

import com.moistAbes.projectManager.exceptions.ProjectNotFoundException;
import com.moistAbes.projectManager.exceptions.TaskNotFoundException;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException exception){
        return new ResponseEntity<>("Project with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException exception){
        return new ResponseEntity<>("Task with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>("User with given id doesn't exist", HttpStatus.NOT_FOUND);
    }
}
