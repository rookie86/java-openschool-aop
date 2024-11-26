package com.openschool.aop.controller;

import com.openschool.aop.dto.TaskDto;
import com.openschool.aop.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    public TaskDto saveNewTask(@RequestBody TaskDto taskDto) {
        return taskService.saveNewTask(taskDto);
    }

    @GetMapping("/")
    public List<TaskDto> getTaskById() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable("taskId") Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTaskById(@PathVariable("taskId") Long taskId, @RequestBody TaskDto taskDto) {
        taskService.updateTaskById(taskId, taskDto);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
    }

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
