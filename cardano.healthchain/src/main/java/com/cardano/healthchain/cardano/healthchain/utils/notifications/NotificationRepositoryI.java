package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;

import java.util.ArrayList;

public interface NotificationRepositoryI {
    ArrayList<NotificationResponse> getNotificationsForEntityById(int page, String entity_id);
    void markNotificationAsRead(String notificationId);
    void insertNotificationForEntity(String entityId, String title, String message, String notificationLevel, String notificationType);
    void insertNotificationForEntities(ArrayList<String> entityIds, String title, String message, String notificationLevel, String notificationType);
    void deleteNotification(String notificationId);
    void deleteNotificationsForEntity(String entityId);
}