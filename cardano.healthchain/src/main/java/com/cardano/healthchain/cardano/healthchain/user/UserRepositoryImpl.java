package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.HEALTHCHAIN_ROLES;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<UserModel> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(UserModel.class),
                email
        );
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UserModel getUserById(String user_id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<UserModel> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(UserModel.class),
                UUID.fromString(user_id)
        );
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UUID createUser(UserCreateRequest userCreateRequest) {
        String sql = "INSERT INTO users (email, hashed_password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?) RETURNING user_id";
        Object[] args = new Object[]{userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getFirstname(), userCreateRequest.getLastname(), HEALTHCHAIN_ROLES.RESIDENT.name()};
        return jdbcTemplate.queryForObject(
                sql,
                args,
                UUID.class
        );
    }
    @Override
    public void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String user_id) {
        String SQL_USER_UPDATE_PERSONAL = "UPDATE users SET gender = ?, dob = ? WHERE user_id = ?";
        Object[] args = new Object[]{
                userUpdateProfilePersonalDetails.getGender(),
                userUpdateProfilePersonalDetails.getDob(),
                UUID.fromString(user_id)
        };
        jdbcTemplate.update(SQL_USER_UPDATE_PERSONAL, args);
    }
    @Override
    public void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String user_id) {
        String SQL_USER_UPDATE_HEALTH = "UPDATE users SET blood_type = ?,genotype = ?,known_allergies = ?,pre_existing_conditions = ? WHERE user_id = ?";
        Object[] args = new Object[]{
                userUpdateProfileHealthInformation.getBlood_type(),
                userUpdateProfileHealthInformation.getGenotype(),
                userUpdateProfileHealthInformation.getKnown_allergies(),
                userUpdateProfileHealthInformation.getPre_existing_conditions(),
                UUID.fromString(user_id)
        };
        jdbcTemplate.update(SQL_USER_UPDATE_HEALTH, args);
    }
    @Override
    public void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String user_id) {
        String SQL_USER_UPDATE_EMERGENCY = "UPDATE users SET emergency_contact_name = ?,emergency_contact_phone = ?,emergency_contact_rel = ? WHERE user_id = ?";
        Object[] args = new Object[]{
                userUpdateEmergencyInformation.getName(),
                userUpdateEmergencyInformation.getRelationship(),
                userUpdateEmergencyInformation.getPhone_number(),
                UUID.fromString(user_id)
        };
        jdbcTemplate.update(SQL_USER_UPDATE_EMERGENCY, args);
    }
    @Override
    public void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData, String user_id) {
        String SQL_USER_UPDATE_LOCATION = "UPDATE users SET nationality = ?, state_of_origin = ? WHERE user_id = ?";
        Object[] args = new Object[]{
                userUpdateLocationData.getCountry(),
                userUpdateLocationData.getState(),
                UUID.fromString(user_id)
        };
        jdbcTemplate.update(SQL_USER_UPDATE_LOCATION, args);
    }
    @Override
    public void verifyUserAccount(String email) {
        String sql = "UPDATE users SET verified = TRUE WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
    @Override
    public void deleteUserByEmail(String email) {
        String sql = "DELETE FROM users where email = ?";
        jdbcTemplate.update(sql,email);
    }

    @Override
    public void deleteUserById(String user_id) {
        String sql = "DELETE FROM users where user_id = ?";
        jdbcTemplate.update(sql,user_id);
    }

    @Override
    public void existsByWalletAddress(String walletAddress) {
        String sql = "SELECT COUNT(*) FROM users WHERE wallet_address = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, walletAddress);
        if (count != null && count > 0) {
            throw new RuntimeException("Wallet already registered");
        }
    }

    @Override
    public Optional<UserModel> findByWalletAddress(String walletAddress) {
        String sql = "SELECT * FROM users WHERE wallet_address = ?";
        try {
            UserModel user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserModel.class), walletAddress);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UUID createMinimalUserForWalletSignUp(UserModel user) {
        String sql = "INSERT INTO users (role, wallet_address, stake_address, public_key, wallet_network, created_at) VALUES (?, ?, ?, ?, ?, ?) RETURNING user_id";
        Object[] args = new Object[]{
                user.getWallet_address(),
                user.getStake_address(),
                user.getPublic_key(),
                user.getWallet_network(),
                user.getRole(),
                user.getCreated_at()
        };
        return jdbcTemplate.queryForObject(sql,args,UUID.class);
    }

    @Override
    public void updateWalletInfo(UserModel user) {
        String sql = "UPDATE users SET wallet_address = ?, stake_address = ?, public_key = ? WHERE user_id = ?";
        Object[] args = new Object[]{
                user.getWallet_address(),
                user.getStake_address(),
                user.getPublic_key(),
                user.getUser_id()
        };
        int rowsUpdated = jdbcTemplate.update(sql, args);
    }
}