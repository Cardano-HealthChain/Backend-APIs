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
    public ArrayList<NotificationResponse> getNotificationForEntity(String entityId,int page) {
        return notificationRepository.getNotificationsForEntityById(page, entityId);
    }
    public void markNotificationAsRead(String notificationId) {
        notificationRepository.markNotificationAsRead(notificationId);
    }
    public void insertClinicRequestNotification(String user_id, String title, String message, String notification_level, String notification_type) {
        notificationRepository.insertNotificationForEntity(user_id,title,message,notification_level,notification_type);
    }
    public void insertDoctorUploadedRecordForUser(String user_id, String title, String message, String notification_level, String notification_type) {
        notificationRepository.insertNotificationForEntity(user_id,title,message,notification_level,notification_type);
    }
    public void insertUserGrantedPermissionForClinic(String clinicId, String title, String message, String notification_level, String notification_type) {
        notificationRepository.insertNotificationForEntity(clinicId,title,message,notification_level,notification_type);
    }
    public void insertUserRevokedPermissionForClinic(String clinicId, String title, String message, String notification_level, String notification_type) {
        notificationRepository.insertNotificationForEntity(clinicId,title,message,notification_level,notification_type);
    }
}