package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.UserDto;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import com.moistAbes.projectManager.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserMapperTests {

    private final UserMapper userMapper;
    private final UserServiceImpl userService;

    @Autowired
    public UserMapperTests(UserMapper userMapper, UserServiceImpl userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Test
    void testThatMapperCorrectlyMapsFromDtoToEntity() {
        //Given
        UserDto testUser = TestDataUtil.createTestUserDtoA();

        //When
        UserEntity mappedUser = userMapper.mapToUserEntity(testUser);

        //Then
        assertThat(mappedUser.getName()).isEqualTo(testUser.getName());
        assertThat(mappedUser.getSurname()).isEqualTo(testUser.getSurname());

        //Clean up
    }

    @Test
    void testThatMapperCorrectlyMapsFromEntityToDto() {
        //Given
        UserEntity savedUser = userService.saveUser(TestDataUtil.createTestUserA());

        //When
        UserDto mappedUser = userMapper.mapToUserDto(savedUser);

        //Then
        assertThat(mappedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(mappedUser.getName()).isEqualTo(savedUser.getName());
        assertThat(mappedUser.getSurname()).isEqualTo(savedUser.getSurname());

        //Clean up
    }
}
