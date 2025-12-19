package com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos;

import java.util.UUID;

public class NotificationResponse {
    private UUID notification_id;
    private UUID entity_id;
    private String title;
    private String message;
    private String notification_level;
    private String notification_type;
    private boolean read_status;

    public NotificationResponse() {
    }

    public NotificationResponse(UUID notification_id, UUID entity_id, String title, String message, String notification_level, String notification_type, boolean read_status) {
        this.notification_id = notification_id;
        this.entity_id = entity_id;
        this.title = title;
        this.message = message;
        this.notification_level = notification_level;
        this.notification_type = notification_type;
        this.read_status = read_status;
    }

    public UUID getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(UUID notification_id) {
        this.notification_id = notification_id;
    }

    public UUID getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(UUID entity_id) {
        this.entity_id = entity_id;
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

    public String getNotification_level() {
        return notification_level;
    }

    public void setNotification_level(String notification_level) {
        this.notification_level = notification_level;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public boolean isRead_status() {
        return read_status;
    }

    public void setRead_status(boolean read_status) {
        this.read_status = read_status;
    }
    @Override
    public String toString() {
        return "NotificationResponse{" +
                "notification_id=" + notification_id +
                ", entity_id=" + entity_id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", notification_level='" + notification_level + '\'' +
                ", notification_type='" + notification_type + '\'' +
                ", read_status=" + read_status +
                '}';
    }
}