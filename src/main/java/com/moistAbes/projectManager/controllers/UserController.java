package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappers.UserMapper;
import com.moistAbes.projectManager.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserEntity> users = userService.getUsers();

        return ResponseEntity.ok(userMapper.mapToUserDtoList(users));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
        UserEntity user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @GetMapping(path = "/project/{projectId}")
    public ResponseEntity<List<UserDto>> getUsersWithProjectId(@PathVariable Long projectId){
        List<UserDto> users = userService.getUsersWithProjectId(projectId);
        return ResponseEntity.ok(users);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity user = userMapper.mapToUserEntity(userDto);
        System.out.println("DZIALA TO?: " + user.getProjects());
        UserEntity savedUser = userService.saveUser(user);
        return new ResponseEntity<>(userMapper.mapToUserDto(savedUser), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        if (!userService.itExists(userDto.getId())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity updatedUser = userService.saveUser(userMapper.mapToUserEntity(userDto));
        return ResponseEntity.ok(userMapper.mapToUserDto(updatedUser));
    }
}
