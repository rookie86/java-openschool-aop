package com.openschool.aop.kafka;


import com.openschool.aop.dto.TaskStatusNotificationDto;
import com.openschool.aop.service.NotificationService;
import com.openschool.aop.template.NotificationTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaTaskConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTaskConsumer.class.getName());

    private final NotificationService notificationService;

    @KafkaListener(id = "${openschool.kafka.consumer.group-id}",
        topics = "${openschool.kafka.topic.task-status-change}",
        containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<TaskStatusNotificationDto> messageList,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        logger.debug("Task consumer: Обработка новых сообщений");
        for (TaskStatusNotificationDto dto : messageList) {
                    try {
                        logger.debug("new message dto {} ", dto.toString());
                        String subject = NotificationTemplate.taskStatusChangedSubject;
                        String messageBody = String.format(NotificationTemplate.taskStatusChangedBody, dto.getId(), dto.getStatus());
                        notificationService.sendEmailNotification("user@openschool.testproject", subject, messageBody);
                    } catch (Exception ex) {
                        String subject = "Exception in KafkaTaskConsumer";
                        String messageBody = "dto + " + dto.toString() + "\r\n" + ex.getMessage() + "\r\n" + ex;
                        notificationService.sendEmailNotification("admin@openschool.testproject", subject, messageBody);
                        logger.error(ex.getMessage(), ex);
                    }
                }
        ack.acknowledge();
        logger.debug("Task consumer: записи обработаны");
    }

    public KafkaTaskConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
