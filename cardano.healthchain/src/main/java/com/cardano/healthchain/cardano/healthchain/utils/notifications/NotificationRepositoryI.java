package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;

import java.util.ArrayList;

public interface NotificationRepositoryI {
    ArrayList<NotificationResponse> getNotificationsForUser(int page, String user_email);

    void markNotificationAsRead(String notificationId);
}
