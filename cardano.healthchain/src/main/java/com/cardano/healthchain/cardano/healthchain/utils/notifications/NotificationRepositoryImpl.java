package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class NotificationRepositoryImpl implements NotificationRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<NotificationResponse> getNotificationsForEntityById(int page, String entity_id) {
        int pageNumber = Math.max(page, 1);
        int offset = (pageNumber - 1) * 10;
        String sql = "SELECT * FROM notifications WHERE entity_id = ? AND read_status = false ORDER BY sent_at DESC LIMIT 10 OFFSET ?";
        Object[] args = new Object[]{UUID.fromString(entity_id), offset};
        return (ArrayList<NotificationResponse>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NotificationResponse.class), args);
    }

    @Override
    public void markNotificationAsRead(String notificationId) {
        String sql = "UPDATE notifications SET read_status = true WHERE notification_id = ?";
        jdbcTemplate.update(sql, UUID.fromString(notificationId));
    }

    @Override
    public void insertNotificationForEntity(String entityId, String title, String message, String notificationLevel, String notificationType) {
        String sql = "INSERT INTO notifications (entity_id, title, message, notification_level, notification_type, sent_at, read_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.fromString(entityId), title, message, notificationLevel, notificationType, LocalDateTime.now(), false);
    }

    @Override
    public void insertNotificationForEntities(ArrayList<String> entityIds, String title, String message, String notificationLevel, String notificationType) {
        String sql = "INSERT INTO notifications (entity_id, title, message, notification_level, notification_type, sent_at, read_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        for (String entityId : entityIds) {
            jdbcTemplate.update(sql, UUID.fromString(entityId), title, message, notificationLevel, notificationType, LocalDateTime.now(), false);
        }
    }

    @Override
    public void deleteNotification(String notificationId) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        jdbcTemplate.update(sql, UUID.fromString(notificationId));
    }

    @Override
    public void deleteNotificationsForEntity(String entityId) {
        String sql = "DELETE FROM notifications WHERE entity_id = ?";
        jdbcTemplate.update(sql, UUID.fromString(entityId));
    }
}