package com.openschool.aop.kafka;

import com.openschool.aop.dto.TaskStatusNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTaskProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTaskProducer.class.getName());

    private final KafkaTemplate template;

    public void sendTo(String topic, TaskStatusNotificationDto taskStatusNotificationDto) {
        try {
            template.send(topic, taskStatusNotificationDto).get();
            template.flush();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public KafkaTaskProducer(KafkaTemplate template) {
        this.template = template;
    }
}
