package com.openschool.aop.mapper;

import com.openschool.aop.dto.TaskDto;
import com.openschool.aop.entity.Task;

public class TaskMapper {

    public static Task toEntity(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getTitle(),
                taskDto.getDescription(), taskDto.getUserId(), taskDto.getStatus());
    }

    public static TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(),
                task.getDescription(), task.getUserId(), task.getStatus());
    }

}
