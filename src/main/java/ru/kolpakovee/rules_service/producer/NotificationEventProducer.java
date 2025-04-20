package ru.kolpakovee.rules_service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.kolpakovee.rules_service.enums.NotificationCategory;
import ru.kolpakovee.rules_service.records.NotificationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationEventProducer {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void send(UUID userId, String message) {
        NotificationEvent event = NotificationEvent.builder()
                .category(NotificationCategory.RULE)
                .message(message)
                .userId(userId)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(topic, event);
    }
}
