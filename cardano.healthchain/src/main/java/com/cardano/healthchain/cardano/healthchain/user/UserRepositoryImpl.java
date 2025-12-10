package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public void createUser(UserCreateRequest userCreateRequest) {
        String SQL_USER_CREATION_STRING = "INSERT INTO users (email,hashed_password,first_name,last_name,role) VALUES (?,?,?,?,?)";
        Object[] args = new Object[]{userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getFirstname(), userCreateRequest.getLastname(),"RESIDENT"};
        jdbcTemplate.update(SQL_USER_CREATION_STRING,args);
    }
    @Override
    public void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String email) {
        String SQL_USER_UPDATE_PERSONAL = "UPDATE users SET gender = ?,dob = ? WHERE email = ?";
        Object[] args = new Object[]{
                userUpdateProfilePersonalDetails.getGender(),
                userUpdateProfilePersonalDetails.getDob(),
                email
        };
        jdbcTemplate.update(SQL_USER_UPDATE_PERSONAL, args);
    }
    @Override
    public void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String email) {
        String SQL_USER_UPDATE_HEALTH = "UPDATE users SET blood_type = ?,genotype = ?,known_allergies = ?,pre_existing_conditions = ? WHERE email = ?";
        Object[] args = new Object[]{
                userUpdateProfileHealthInformation.getBlood_type(),
                userUpdateProfileHealthInformation.getGenotype(),
                userUpdateProfileHealthInformation.getKnown_allergies(),
                userUpdateProfileHealthInformation.getPre_existing_conditions(),
                email
        };
        jdbcTemplate.update(SQL_USER_UPDATE_HEALTH, args);
    }
    @Override
    public void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String email) {
        String SQL_USER_UPDATE_EMERGENCY = "UPDATE users SET emergency_contact_name = ?,emergency_contact_phone = ?,emergency_contact_rel = ? WHERE email = ?";
        Object[] args = new Object[]{
                userUpdateEmergencyInformation.getName(),
                userUpdateEmergencyInformation.getRelationship(),
                userUpdateEmergencyInformation.getPhone_number(),
                email
        };
        jdbcTemplate.update(SQL_USER_UPDATE_EMERGENCY, args);
    }
    @Override
    public void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData, String email) {
        String SQL_USER_UPDATE_LOCATION = "UPDATE users SET nationality = ?, state_of_origin = ? WHERE email = ?";
        Object[] args = new Object[]{
                userUpdateLocationData.getCountry(),
                userUpdateLocationData.getState(),
                email
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
}