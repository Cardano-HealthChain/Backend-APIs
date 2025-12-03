package com.cardano.healthchain.cardano.healthchain.utils.notifications;

import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
@Repository
public class NotificationRepositoryImpl implements NotificationRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<NotificationResponse> getNotificationsForUser(int page, String category, String user_email) {
        int offset = (page - 1) * 5;
        String getNotificationSqlStatement = "SELECT * FROM notifications where notification_type = ?, user_email = ? ORDER BY notification_id LIMIT 10 OFFSET ?";
        Object[] args = new Object[]{category,user_email,offset};
        return (ArrayList<NotificationResponse>) jdbcTemplate.query(
                getNotificationSqlStatement,
                new BeanPropertyRowMapper<>(NotificationResponse.class),
                args
        );
    }
    @Override
    public void markNotificationAsRead(String notificationId) {
        String markNotificationAsReadSqlStatement = "UPDATE notifications set read_status = true WHERE notification_id = ?";
        Object[] args = new Object[]{notificationId};
        int update = jdbcTemplate.update(markNotificationAsReadSqlStatement, args);
    }
}
