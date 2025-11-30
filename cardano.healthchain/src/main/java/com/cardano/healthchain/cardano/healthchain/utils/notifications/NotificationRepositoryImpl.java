package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

public class NotificationRepositoryImpl implements NotificationRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<NotificationResponse> getNotificationsForUser(int page, String category, String userId) {
        return null;
    }

    @Override
    public boolean markNotificationAsRead(String notificationId) {
        return false;
    }
}
