package com.moistAbes.projectManager.mappersv2;

import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper2 {

    private final TaskRepository taskRepository;

    public UserEntity mapToUserEntity(final UserDto userDto){
        return UserEntity.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .build();
    }

    public UserDto mapToUserDto(final UserEntity userEntity) {
        return  UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
//                .tasks(taskRepository.findByUserId(userEntity.getId()).stream()
//                        .map(TaskEntity::getId)
//                        .collect(Collectors.toList()))
                .build();
    }

    public List<UserDto> mapToUserDtoList(final List<UserEntity> userEntities){
        return userEntities.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
