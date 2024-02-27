package com.moistAbes.projectManager.services;

import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserEntity saveUser(UserEntity userEntity);
    UserEntity getUser(Long id) throws UserNotFoundException;
    List<UserEntity> getUsers();
    boolean itExists(Long id);
    void deleteUser(Long id);



}
