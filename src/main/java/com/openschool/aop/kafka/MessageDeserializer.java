package com.openschool.aop.kafka;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MessageDeserializer<T> extends JsonDeserializer<T> {

    private static final Logger logger = LoggerFactory.getLogger(MessageDeserializer.class.getName());

    private static String getMessage(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, headers, data);
        } catch (Exception ex) {
            String dataString = new String(data, StandardCharsets.UTF_8);
            logger.error("Произошла ошибка во время десереализации сообщения {}", dataString, ex);
            throw new SerializationException("\"Произошла ошибка во время десереализации сообщения " + dataString + " в топике [" + topic + "]", ex);
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (Exception ex) {
            String dataString = new String(data, StandardCharsets.UTF_8);
            logger.error("Произошла ошибка во время десереализации сообщения {}", new String(data, StandardCharsets.UTF_8), ex);
            throw new SerializationException("\"Произошла ошибка во время десереализации сообщения " + dataString + " в топике [" + topic + "]", ex);
        }
    }
}
