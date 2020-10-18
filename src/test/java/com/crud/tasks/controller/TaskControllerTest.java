package com.crud.tasks.controller;

import com.crud.tasks.domain.task.Task;
import com.crud.tasks.domain.task.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void getTasksTest() throws Exception {
        //given
        List<TaskDto> taskDtoList = new ArrayList<>(Arrays.asList(
                new TaskDto(1L, "test", "test"),
                new TaskDto(2L, "test", "test"),
                new TaskDto(3L, "test", "test")
        ));
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(taskDtoList);

        //when $ then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].content", is("test")));

        verify(dbService, times(1)).getAllTasks();
    }

    @Test
    public void getTaskTest() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test");
        Optional<Task> taskOptional = Optional.of(new Task());
        when(dbService.getTaskById(anyLong())).thenReturn(taskOptional);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any())).thenReturn(taskDto);
        //when $ then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));

        verify(dbService, times(1)).getTaskById(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteTaskTest() throws Exception {
        //given
        Task task = new Task(1L, "Test", "Test");
        Optional<Task> taskOptional = Optional.of(task);
        when(dbService.getTaskById(anyLong())).thenReturn(taskOptional);
        //when $ then
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteTask(ArgumentMatchers.any());
    }

    @Test
    public void updateTaskTest() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test");
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any())).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonTaskDto = gson.toJson(taskDto);

        //when $ then
        mockMvc.perform(put("/v1/task/updateTask")
                .content(jsonTaskDto)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));

        verify(dbService, times(1)).saveTask(ArgumentMatchers.any());

    }

    @Test
    public void createTaskTest() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test");
        when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(new Task());
        Gson gson = new Gson();
        String jsonTaskDto = gson.toJson(taskDto);

        //when $ then
        mockMvc.perform(post("/v1/task/createTask")
                .content(jsonTaskDto)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(dbService, times(1)).saveTask(ArgumentMatchers.any());
    }
}