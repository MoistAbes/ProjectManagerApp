package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.mappers.UserMapper;
import com.moistAbes.projectManager.repositories.UserRepository;
import com.moistAbes.projectManager.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public List<UserDto> getUsersWithProjectId(Long projectId){
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::mapToUserDto)
                .filter(userDto -> userDto.getProjectsId().contains(projectId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean itExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
