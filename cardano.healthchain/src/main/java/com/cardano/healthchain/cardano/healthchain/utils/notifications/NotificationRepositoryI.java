package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;

import java.util.ArrayList;

public interface NotificationRepositoryI {
    ArrayList<NotificationResponse> getNotificationsForUser(int page, String category, String userId);

    boolean markNotificationAsRead(String notificationId);
}
