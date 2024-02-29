package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTests {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testThatUserCanBeCreatedAndRecalled() {
        //Given

        //When
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());
        Optional<UserEntity> resultUser = userRepository.findById(savedUser.getId());

        //Then
        assertThat(resultUser).isPresent();
        assertThat(resultUser.get().getName()).isEqualTo(savedUser.getName());
        assertThat(resultUser.get().getSurname()).isEqualTo(savedUser.getSurname());

        //Clean up
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void testThatMultipleUsersCanBeCreatedAndRecalled() {
        //Given

        //When
        UserEntity savedUserA = userRepository.save(TestDataUtil.createTestUserA());
        UserEntity savedUserB = userRepository.save(TestDataUtil.createTestUserB());
        UserEntity savedUserC = userRepository.save(TestDataUtil.createTestUserC());

        Optional<UserEntity> resultUserA = userRepository.findById(savedUserA.getId());
        Optional<UserEntity> resultUserB = userRepository.findById(savedUserB.getId());
        Optional<UserEntity> resultUserC = userRepository.findById(savedUserC.getId());

        //Then
        assertThat(resultUserA).isPresent();
        assertThat(resultUserA.get().getName()).isEqualTo(savedUserA.getName());
        assertThat(resultUserA.get().getSurname()).isEqualTo(savedUserA.getSurname());

        assertThat(resultUserB).isPresent();
        assertThat(resultUserB.get().getName()).isEqualTo(savedUserB.getName());
        assertThat(resultUserB.get().getSurname()).isEqualTo(savedUserB.getSurname());

        assertThat(resultUserC).isPresent();
        assertThat(resultUserC.get().getName()).isEqualTo(savedUserC.getName());
        assertThat(resultUserC.get().getSurname()).isEqualTo(savedUserC.getSurname());

        //Clean up
        userRepository.deleteById(savedUserA.getId());
        userRepository.deleteById(savedUserB.getId());
        userRepository.deleteById(savedUserC.getId());
    }

    @Test
    void testThatUserCanBeUpdated() {
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        //When
        savedUser.setName("Updated name");
        UserEntity updatedUser = userRepository.save(savedUser);
        Optional<UserEntity> resultUser = userRepository.findById(updatedUser.getId());

        //Then
        assertThat(resultUser).isPresent();
        assertThat(resultUser.get().getName()).isEqualTo("Updated name");

        //Clean up
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    void testThatUserCanBeDeleted() {
        //Given
        UserEntity savedUser = userRepository.save(TestDataUtil.createTestUserA());

        //When
        userRepository.deleteById(savedUser.getId());
        Optional<UserEntity> resultUser = userRepository.findById(savedUser.getId());

        //Then
        assertThat(resultUser).isEmpty();

        //Clean up
    }
}
