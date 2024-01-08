package com.moistAbes.projectManager.repositories;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryIntegrationTest {

    private TaskRepository underTest;

    @Autowired
    public TaskRepositoryIntegrationTest(TaskRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatTaskCanBeCreatedAndRecalled(){
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        TaskEntity savedTestTaskA = underTest.save(testTaskA);

        Optional<TaskEntity> result = underTest.findById(savedTestTaskA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testTaskA);

        underTest.deleteById(savedTestTaskA.getId());
    }

    @Test
    public void testThatMultipleTasksCanBeCreatedAndRecalled(){
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        TaskEntity testTaskB = TestDataUtil.createTestTaskB();
        TaskEntity testTaskC = TestDataUtil.createTestTaskC();

        TaskEntity savedTestTaskA = underTest.save(testTaskA);
        TaskEntity savedTestTaskB = underTest.save(testTaskB);
        TaskEntity savedTestTaskC = underTest.save(testTaskC);

        Optional<TaskEntity> resultA = underTest.findById(savedTestTaskA.getId());
        Optional<TaskEntity> resultB = underTest.findById(savedTestTaskB.getId());
        Optional<TaskEntity> resultC = underTest.findById(savedTestTaskC.getId());

        assertThat(resultA).isPresent();
        assertThat(resultB).isPresent();
        assertThat(resultC).isPresent();

        assertThat(resultA.get()).isEqualTo(testTaskA);
        assertThat(resultB.get()).isEqualTo(testTaskB);
        assertThat(resultC.get()).isEqualTo(testTaskC);

        underTest.deleteById(savedTestTaskA.getId());
        underTest.deleteById(savedTestTaskB.getId());
        underTest.deleteById(savedTestTaskC.getId());
    }

    @Test
    public void testThatTaskCanBeUpdated(){
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        TaskEntity savedTestTaskA = underTest.save(testTaskA);

        savedTestTaskA.setTitle("UPDATED");

        TaskEntity updatedTestTaskA = underTest.save(savedTestTaskA);
        Optional<TaskEntity> result = underTest.findById(updatedTestTaskA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedTestTaskA);

        underTest.deleteById(updatedTestTaskA.getId());
    }

    @Test
    public void testThatTaskCanBeDeleted(){
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        TaskEntity savedTestTaskA = underTest.save(testTaskA);

        underTest.deleteById(savedTestTaskA.getId());

        Optional<TaskEntity> result = underTest.findById(savedTestTaskA.getId());

        assertThat(result).isEmpty();
    }

}
