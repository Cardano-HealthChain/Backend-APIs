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
    public ArrayList<NotificationResponse> getNotificationsForUser(int page, String category, String userId) {
        return notificationRepository.getNotificationsForUser(page, category, userId);
    }

    public boolean markNotificationAsRead(String notificationId) {
        return notificationRepository.markNotificationAsRead(notificationId);
    }
}
