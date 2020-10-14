package com.crud.tasks.mapper;

import com.crud.tasks.domain.task.Task;
import com.crud.tasks.domain.task.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        //given
        TaskDto taskDto = new TaskDto(1L, "test", "test");

        //when
        Task mapedTask = taskMapper.mapToTask(taskDto);

        //then
        Assert.assertEquals(mapedTask.getId(), taskDto.getId());
        Assert.assertEquals(mapedTask.getContent(), taskDto.getContent());
        Assert.assertEquals(mapedTask.getTitle(), taskDto.getTitle());

    }

    @Test
    public void mapToTaskDtoTest() {
        //given
        Task task = new Task(1L, "test", "test");

        //when
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //then
        Assert.assertEquals(task.getId(), taskDto.getId());
        Assert.assertEquals(task.getContent(), taskDto.getContent());
        Assert.assertEquals(task.getTitle(), taskDto.getTitle());

    }

    @Test
    public void mapToTaskDtoListTest() {
        //given
        Task task1 = new Task(1L, "test", "test");
        Task task2 = new Task(2L, "test", "test");
        Task task3 = new Task(3L, "test", "test");
        List<Task> tasks = new ArrayList<>(Arrays.asList(task1, task2, task3));

        //when
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //then
        Assert.assertEquals(taskDtos.size(), tasks.size());
        Assert.assertTrue(taskDtos.contains(new TaskDto(task1.getId(), task1.getTitle(), task1.getContent())));
        Assert.assertTrue(taskDtos.contains(new TaskDto(task2.getId(), task2.getTitle(), task2.getContent())));
        Assert.assertTrue(taskDtos.contains(new TaskDto(task3.getId(), task3.getTitle(), task3.getContent())));

    }
}