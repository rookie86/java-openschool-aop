package com.openschool.aop.controller;

import com.openschool.aop.entity.Task;
import com.openschool.aop.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Task> saveNewTask(@RequestBody Task task) {
        Task newTask = taskService.saveNewTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getTaskById() {
        List<Task> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") Long taskId) {
        Task task = taskService.findTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity updateTaskById(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        taskService.updateTaskById(taskId, task);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTaskById(@PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
}
