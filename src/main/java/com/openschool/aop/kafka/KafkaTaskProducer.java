package com.openschool.aop.kafka;

import com.openschool.aop.dto.TaskStatusNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaTaskProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTaskProducer.class.getName());

    private final KafkaTemplate template;

    public KafkaTaskProducer(KafkaTemplate template) {
        this.template = template;
    }

    public void sendTo(String topic, TaskStatusNotificationDto taskStatusNotificationDto) {
        try {
            CompletableFuture<SendResult<String, TaskStatusNotificationDto>> future = template.send(topic, taskStatusNotificationDto);

            future.whenComplete((result, e) -> {
                if (e == null) {
                    //TODO
                    //log.info(String.format("Published event to topic %s: key = %s value = %s", topicConfig.getName(), key, value.getName().toString()));
                } else {
                    //log.error(e.getMessage());
                    throw new RuntimeException("Caught an exception", e);
                }
            });

            template.flush();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
