package com.openschool.aop.service;

import com.openschool.aop.aspect.LogArgs;
import com.openschool.aop.aspect.LogException;
import com.openschool.aop.aspect.LogExecutionTime;
import com.openschool.aop.entity.Task;
import com.openschool.aop.repository.TaskRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @LogArgs
    @LogException
    public Task saveNewTask(Task task) {
        try {
            return taskRepository.save(task);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @LogException
    @LogExecutionTime
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @LogArgs
    @LogException
    @LogExecutionTime
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @LogArgs
    @LogException
    @LogExecutionTime
    public void updateTaskById(Long id, Task task) {
        Task taskDb = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (task.getTitle() != null) {
            taskDb.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            taskDb.setDescription(task.getDescription());
        }
        if (task.getUserId() != null) {
            taskDb.setUserId(task.getUserId());
        }
        taskRepository.save(taskDb);
    }

    @LogException
    @LogExecutionTime
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
