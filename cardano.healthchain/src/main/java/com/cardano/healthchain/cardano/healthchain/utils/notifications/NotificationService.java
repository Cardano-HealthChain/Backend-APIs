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

    public void insertClinicRequestNotification(String userEmail, String title, String message, String notification_level, String notification_type) {
        notificationRepository.insertClinicRequestNotification(userEmail,title,message,notification_level,notification_type);
    }
    public ArrayList<NotificationResponse> getNotificationsForClinic(String name) {
        return null;
    }
}
