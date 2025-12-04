package com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationSeverityLevel;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class NotificationResponse {
    private String notification_id;
    private String title;
    private String message;
    private NotificationSeverityLevel notification_level;
    private NotificationTypes notification_type;
    private LocalDateTime sent_at;

    public NotificationResponse(String notification_id, String title, String message, NotificationSeverityLevel notification_level, NotificationTypes notification_type, LocalDateTime sent_at) {
        this.notification_id = notification_id;
        this.title = title;
        this.message = message;
        this.notification_level = notification_level;
        this.notification_type = notification_type;
        this.sent_at = sent_at;
    }

    public NotificationResponse() {
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationSeverityLevel getNotification_level() {
        return notification_level;
    }

    public void setNotification_level(NotificationSeverityLevel notification_level) {
        this.notification_level = notification_level;
    }

    public NotificationTypes getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(NotificationTypes notification_type) {
        this.notification_type = notification_type;
    }

    public LocalDateTime getSent_at() {
        return sent_at;
    }

    public void setSent_at(LocalDateTime sent_at) {
        this.sent_at = sent_at;
    }
}
