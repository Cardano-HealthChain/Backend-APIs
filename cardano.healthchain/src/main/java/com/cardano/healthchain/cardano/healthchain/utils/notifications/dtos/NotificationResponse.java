package com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationSeverityLevel;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private String notification_id;
    private String title;
    private String description;
    private NotificationSeverityLevel notification_level;
    private NotificationTypes notification_type;
    private LocalDateTime sent_at;
}
