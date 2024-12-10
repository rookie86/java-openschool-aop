package com.openschool.aop.dto;

import com.openschool.aop.enums.TaskStatus;

public class TaskStatusNotificationDto {

    private Long id;

    private TaskStatus status;

    public Long getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskStatusNotificationDto() {
    }

    public TaskStatusNotificationDto(Long id, TaskStatus status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskStatusNotificationDto{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
