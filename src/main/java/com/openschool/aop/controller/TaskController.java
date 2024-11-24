package com.openschool.aop.controller;

import com.openschool.aop.entity.Task;
import com.openschool.aop.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    public Task saveNewTask(@RequestBody Task task) {
        return taskService.saveNewTask(task);
    }

    @GetMapping("/")
    public List<Task> getTaskById() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable("taskId") Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTaskById(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        taskService.updateTaskById(taskId, task);
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
