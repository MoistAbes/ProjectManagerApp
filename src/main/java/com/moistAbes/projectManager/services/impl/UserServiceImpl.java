package com.moistAbes.projectManager.services.impl;

import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;
import com.moistAbes.projectManager.repositories.UserRepository;
import com.moistAbes.projectManager.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    @Override
    public boolean itExists(Long id) {
        return userRepository.existsById(id);
    }
}
