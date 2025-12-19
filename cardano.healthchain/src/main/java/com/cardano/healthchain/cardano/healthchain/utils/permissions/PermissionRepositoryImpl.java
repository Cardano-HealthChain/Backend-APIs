package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.enums.PermissionAccessScopeEnum;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public ArrayList<PermissionDataResponse> getPermittedClinicsForUserById(String userId, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT * FROM permissions WHERE user_id = ? AND granted = true AND revoked = false ORDER BY granted_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<PermissionDataResponse>) jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PermissionDataResponse.class), UUID.fromString(userId), offset);
    }
    @Override
    public ArrayList<PermissionDataResponse> getRequestedPermissionsForUserById(String userId, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT * FROM permissions WHERE user_id = ? AND granted = false AND revoked = false ORDER BY granted_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<PermissionDataResponse>) jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PermissionDataResponse.class), UUID.fromString(userId), offset);
    }

    @Override
    public ArrayList<PermissionDataResponse> getClinicPermissionRequestsById(String clinicId, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT * FROM permissions WHERE clinic_id = ? AND granted = false AND revoked = false ORDER BY granted_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<PermissionDataResponse>) jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PermissionDataResponse.class), UUID.fromString(clinicId), offset);
    }

    @Override
    public ArrayList<UserDataProfileResponse> getUsersWhoPermittedClinicById(String clinicId, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT u.* FROM permissions p JOIN users u ON u.user_id = p.user_id WHERE p.clinic_id = ? AND p.granted = true AND p.revoked = false ORDER BY p.granted_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<UserDataProfileResponse>) jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserDataProfileResponse.class), UUID.fromString(clinicId), offset);
    }

    @Override
    public void doctorRequestUserPermission(String doctorId, String userId, String permissionAccessScopeEnum, DoctorDataResponse doctorRequestingPermission, ClinicDataResponse clinicDoctorIsRegisteredUnder) {
        String sql = "INSERT INTO permissions (user_id, clinic_id, doctor_id, clinic_name, doctor_name, access_type) VALUES (?,?,?,?,?,?)";
        Object[] args = new Object[]{
                UUID.fromString(userId),
                doctorRequestingPermission.getClinic_id(),
                UUID.fromString(doctorId),
                clinicDoctorIsRegisteredUnder.getClinic_name(),
                doctorRequestingPermission.getFirst_name().concat(" ".concat(doctorRequestingPermission.getLast_name())),
                permissionAccessScopeEnum
        };
        jdbcTemplate.update(sql,args);
    }
    @Override
    public void userPermitClinic(String clinicId, String userId, LocalDateTime expires) {
        String sql = "UPDATE permissions SET granted = true, granted_at = ?, expires_at = ? WHERE user_id = ? AND clinic_id = ? AND revoked = false";
        Object[] args = new Object[]{LocalDateTime.now(),expires, UUID.fromString(userId), UUID.fromString(clinicId)};
        jdbcTemplate.update(sql,args);
    }
    @Override
    public void userRevokeClinic(String clinicId, String userId) {
        String sql = "UPDATE permissions SET revoked = true, revoked_at = ? WHERE user_id = ? AND clinic_id = ? AND revoked = false";
        Object[] args = new Object[]{LocalDateTime.now(), UUID.fromString(userId), UUID.fromString(clinicId)};
        jdbcTemplate.update(sql,args);
    }
    @Override
    public void userDeleteClinicPermissionRequest(String userId, String clinicId) {
        String sql = "DELETE FROM permissions WHERE user_id = ? AND clinic_id = ? AND granted = false AND revoked = false";
        Object[] args = new Object[]{UUID.fromString(userId), UUID.fromString(clinicId)};
        jdbcTemplate.update(sql,args);
    }
    @Override
    public void clinicDeletePermissionRequest(String userId, String clinicId) {
        String sql = "DELETE FROM permissions WHERE user_id = ? AND clinic_id = ? AND granted = false AND revoked = false";
        Object[] args = new Object[]{UUID.fromString(userId), UUID.fromString(clinicId)};
        jdbcTemplate.update(sql,args);
    }
    @Override
    public boolean clinicHasWritePermissionForUser(String userId, String clinicId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM permissions WHERE user_id = ? AND clinic_id = ? AND granted = true AND revoked = false AND access_type = ?)";
        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                UUID.fromString(userId),
                UUID.fromString(clinicId),
                PermissionAccessScopeEnum.WRITE.name()
        );
        return Boolean.TRUE.equals(exists);
    }
    @Override
    public boolean clinicHasReadPermissionForUser(String userId, String clinicId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM permissions WHERE user_id = ? AND clinic_id = ? AND granted = true AND revoked = false AND access_type = ?)";
        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                UUID.fromString(userId),
                UUID.fromString(clinicId),
                PermissionAccessScopeEnum.READ.name()
        );
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public boolean clinicHasReadAndWritePermissionForUser(String userId, String clinicId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM permissions WHERE user_id = ? AND clinic_id = ? AND granted = true AND revoked = false AND access_type = ?)";
        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                UUID.fromString(userId),
                UUID.fromString(clinicId),
                PermissionAccessScopeEnum.READANDWRITE.name()
        );
        return Boolean.TRUE.equals(exists);
    }
}