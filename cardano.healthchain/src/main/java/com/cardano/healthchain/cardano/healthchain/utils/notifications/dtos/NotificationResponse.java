package com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationSeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotificationResponse {
    public String NotificationId;
    public String title;
    public String description;
    public NotificationSeverityLevel notificationSeverityLevel;
}
