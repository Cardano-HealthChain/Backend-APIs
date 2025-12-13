package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_id, int page) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getPermittedClinicsSql = "SELECT o.*, u.* FROM permissions o JOIN clinics u ON o.clinic_id = u.clinic_id WHERE user_id = ? AND granted = true ORDER BY o.granted_at DESC LIMIT 5 OFFSET ?";
        Object[] args = new Object[]{UUID.fromString(user_id),offset};
        return (ArrayList<PermissionResponse>) jdbcTemplate.query(
                getPermittedClinicsSql,
                new BeanPropertyRowMapper<>(PermissionResponse.class),
                args
        );
    }
    @Override
    public ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page) {
        int offset = (page - 1) * 5;
        String getPermittedClinicsSql = "SELECT * FROM permissions WHERE clinic_id = ? ORDER BY clinic_id LIMIT 5 offset ?";
        Object[] args = new Object[]{UUID.fromString(clinicId),offset};
        return (ArrayList<PermissionResponse>) jdbcTemplate.query(
                getPermittedClinicsSql,
                new BeanPropertyRowMapper<>(PermissionResponse.class),
                args
        );
    }
    @Override
    public void revokeClinicPermissionForUser(String clinicId, String user_id) {
        String permitClinicSqlStatement = "UPDATE permissions set revoked = true, revoked_at = ? WHERE user_id = ? AND clinic_id = ?";
        Object[] args = new Object[]{Instant.now(), UUID.fromString(user_id),clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
        //log number of rows updated
    }

    @Override
    public void clinicRequestUserPermission(String clinicEmail, String user_id, String accessType) {

    }
    @Override
    public ArrayList<PermissionResponse> getClinicPermissionRequests(String user_id, int page) {
        int offset = (page - 1) * 5;
        String getClinicPermissionRequestsSqlStatement = "SELECT * FROM permissions where user_id = ? AND revoked = false AND granted = false ORDER BY permissions_id OFFSET ? LIMIT 5";
        return (ArrayList<PermissionResponse>) jdbcTemplate.query(getClinicPermissionRequestsSqlStatement,new BeanPropertyRowMapper<>(PermissionResponse.class), UUID.fromString(user_id),offset);
    }
    @Override
    public void deletePermissionRequestByClinic(String user_id, String clinicId) {
        String permitClinicSqlStatement = "DELETE from permissions WHERE user_id = ? AND clinic_id = ?";
        Object[] args = new Object[]{UUID.fromString(user_id),clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
        //log number of rows affected
    }

    @Override
    public void permitClinic(String clinicId, String user_id, Instant Expires, String permissionAccessScopes) {
        String permitClinicSqlStatement = "UPDATE permissions set granted = true, granted_at = ?, access_type = ?,expires_at = ? WHERE user_id = ? AND clinic_id = ?";
        Object[] args = new Object[]{Instant.now(), permissionAccessScopes, LocalDateTime.now().plusDays(1),UUID.fromString(user_id),clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
    }
}