package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_email, int page) {
        int offset = (page - 1) * 5;
        String getPermittedClinicsSql = "SELECT o.*, u.* FROM permissions o JOIN clinics u on o.clinic_id = u.clinic_id WHERE user_email = ? ORDER BY clinic_id LIMIT 5 offset ?";
        Object[] args = new Object[]{user_email,offset};
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
        Object[] args = new Object[]{clinicId,offset};
        return (ArrayList<PermissionResponse>) jdbcTemplate.query(
                getPermittedClinicsSql,
                new BeanPropertyRowMapper<>(PermissionResponse.class),
                args
        );
    }
    @Override
    public void revokeClinicPermissionForUser(String clinicId, String user_email) {
        String permitClinicSqlStatement = "UPDATE permissions set revoked = true, revoked_at = ? WHERE email = ? AND clinic_id = ?";
        Object[] args = new Object[]{Instant.now(), user_email,clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
        //log number of rows updated
    }

    @Override
    public void deletePermissionRequestByClinic(String user_email, String clinicId) {
        String permitClinicSqlStatement = "DELETE from permissions WHERE email = ? AND clinic_id = ?";
        Object[] args = new Object[]{user_email,clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
        //log number of rows affected
    }

    @Override
    public void permitClinic(String clinicId, String user_email, Instant Expires, String permissionAccessScopes) {
        String permitClinicSqlStatement = "UPDATE permissions set granted = true, granted_at = ?, access_type = ?,expires_at = ? WHERE email = ? AND clinic_id = ?";
        Object[] args = new Object[]{Instant.now(), permissionAccessScopes, Instant.now().plus(1, ChronoUnit.DAYS),user_email,clinicId};
        int update = jdbcTemplate.update(permitClinicSqlStatement, args);
    }

}