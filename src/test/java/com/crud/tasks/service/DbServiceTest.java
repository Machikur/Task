package com.crud.tasks.service;

import com.crud.tasks.domain.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DbServiceTest {

    @Autowired
    private DbService dbService;


    @Test
    public void saveTest() {
        //given
        Task task = new Task();

        //when
        dbService.saveTask(task);
        long id = task.getId();

        //then
        Assert.assertTrue(dbService.getTaskById(id).isPresent());

        //cleanUp
        dbService.deleteTask(task);
    }

    @Test
    public void deleteTest() {
        //given
        Task task = new Task();

        //when
        dbService.saveTask(task);
        long id = task.getId();
        boolean wasSaved = dbService.getTaskById(id).isPresent();
        dbService.deleteTask(task);
        boolean isPresent = dbService.getTaskById(id).isPresent();

        //then
        Assert.assertTrue(wasSaved);
        Assert.assertFalse(isPresent);

        //cleanUp
    }

    @Test
    public void updateTest() {
        //given
        Task task = new Task();

        //when
        dbService.saveTask(task);
        long id = task.getId();
        Task newTask = new Task(id, "test", "test");
        dbService.saveTask(newTask);
        Task updatedTask = dbService.getTaskById(id).get();

        //then
        Assert.assertEquals(updatedTask, newTask);
        Assert.assertNotEquals(updatedTask, task);

        //cleanUp
        dbService.deleteTask(task);
    }


    @Test
    public void findAllTest() {
        //given
        Task task = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        //when
        dbService.saveTask(task);
        dbService.saveTask(task2);
        dbService.saveTask(task3);
        List<Task> tasks = dbService.getAllTasks();

        //then
        Assert.assertTrue(tasks.contains(task));
        Assert.assertTrue(tasks.contains(task3));
        Assert.assertTrue(tasks.contains(task2));

        //cleanUp
        dbService.deleteTask(task);
        dbService.deleteTask(task2);
        dbService.deleteTask(task3);
    }


}