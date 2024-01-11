package com.moistAbes.projectManager.mappers;

import com.moistAbes.projectManager.TestDataUtil;
import com.moistAbes.projectManager.domain.dto.ProjectDto;
import com.moistAbes.projectManager.domain.dto.TaskDto;
import com.moistAbes.projectManager.domain.entity.ProjectEntity;
import com.moistAbes.projectManager.mappers.impl.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskMapperTests {

    private final TaskMapper taskMapper;

    @Autowired
    public TaskMapperTests(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    @Test
    public void testThatMapperCorrectlyMapsFromDtoToEntity(){
        //given
        TaskDto testTaskDtoA = TestDataUtil.createTaskDtoA();

    }
}

