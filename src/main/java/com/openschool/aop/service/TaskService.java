package com.openschool.aop.service;

/*import com.openschool.aop.aspect.LogArgs;
import com.openschool.aop.aspect.LogException;
import com.openschool.aop.aspect.LogExecutionTime;*/

import com.openschool.aop.dto.TaskDto;
import com.openschool.aop.dto.TaskStatusNotificationDto;
import com.openschool.aop.entity.Task;
import com.openschool.aop.kafka.KafkaTaskProducer;
import com.openschool.aop.mapper.TaskMapper;
import com.openschool.aop.repository.TaskRepository;
import com.openschoolstarter.starter.aspect.LogArgs;
import com.openschoolstarter.starter.aspect.LogException;
import com.openschoolstarter.starter.aspect.LogExecutionTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final KafkaTaskProducer kafkaTaskProducer;

    @Value("${openschool.kafka.topic.task-status-change}")
    private String taskStatusChangeTopic;

    public TaskService(TaskRepository taskRepository, KafkaTaskProducer kafkaTaskProducer) {
        this.taskRepository = taskRepository;
        this.kafkaTaskProducer = kafkaTaskProducer;
    }

    @LogArgs
    @LogException
    public TaskDto saveNewTask(TaskDto taskDto) {
        Task task = TaskMapper.toEntity(taskDto);
        try {
            Task savedTask = taskRepository.save(task);
            return TaskMapper.toDto(savedTask);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @LogException
    @LogExecutionTime
    public TaskDto findTaskById(Long id) {
            Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskMapper.toDto(task);
    }

    @LogArgs
    @LogException
    @LogExecutionTime
    public List<TaskDto> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtoList = tasks.stream().map(TaskMapper::toDto).toList();
        return taskDtoList;
    }

    @LogArgs
    @LogException
    @LogExecutionTime
    public void updateTaskById(Long id, TaskDto taskDto) {
        Task taskDb = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (taskDto.getTitle() != null) {
            taskDb.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            taskDb.setDescription(taskDto.getDescription());
        }
        if (taskDto.getUserId() != null) {
            taskDb.setUserId(taskDto.getUserId());
        }
        if (taskDto.getStatus() != null) {
            if (!taskDto.getStatus().equals(taskDb.getStatus())) {
                TaskStatusNotificationDto taskStatusNotificationDto = new TaskStatusNotificationDto(id, taskDto.getStatus());
                try {
                    sendStatusChangeMessage(taskStatusNotificationDto);
                } catch (Exception ex) {
                    //TODO logger
                }
            }
            taskDb.setStatus(taskDto.getStatus());
        }
        taskRepository.save(taskDb);
    }

    @LogException
    @LogExecutionTime
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public void sendStatusChangeMessage(TaskStatusNotificationDto taskStatusNotificationDto) {
        kafkaTaskProducer.sendTo(taskStatusChangeTopic, taskStatusNotificationDto);
    }

}
