package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.ArrayList;

//@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String userId) {
        return null;
    }

    @Override
    public PermissionResponse permitClinic(String clinicId, String userId, Instant Expires) {
        return null;
    }

    @Override
    public void revokeClinicPermission(String clinicId) {

    }
}