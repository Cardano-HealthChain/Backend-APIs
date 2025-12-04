package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotificationService {
    private final NotificationRepositoryImpl notificationRepository;
    public NotificationService(NotificationRepositoryImpl notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public ArrayList<NotificationResponse> getNotificationsForUser(int page, String user_email) {
        return notificationRepository.getNotificationsForUser(page, user_email);
    }

    public void markNotificationAsRead(String notificationId) {
        notificationRepository.markNotificationAsRead(notificationId);
    }
}
