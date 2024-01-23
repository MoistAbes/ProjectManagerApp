package com.moistAbes.projectManager.controllers;

import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappersv2.UserMapper2;
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
    private final UserMapper2 userMapper;

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

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity user = userMapper.mapToUserEntity(userDto);
        UserEntity savedUser = userService.saveUser(user);
        return new ResponseEntity<>(userMapper.mapToUserDto(savedUser), HttpStatus.CREATED);
    }



}
