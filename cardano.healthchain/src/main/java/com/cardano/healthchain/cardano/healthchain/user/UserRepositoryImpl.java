package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public class UserRepositoryImpl implements UserRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        String SQL_USER_CREATION_STRING = "INSERT INTO users (email,hashed_password,first_name,last_name) VALUES (?,?,?,?) RETURNING id";
        Object args = new Object[]{userCreateRequest.getEmail(), userCreateRequest.getPassword(), userCreateRequest.getFirstname(), userCreateRequest.getLastname()};
        UUID userId = jdbcTemplate.queryForObject(
                SQL_USER_CREATION_STRING,
                UUID.class, args
        );
        return UserCreateResponse
                .builder()
                .userId(userId)
                //create jwt token
                .token("random for now")
                .build();
    }
    @Override
    public void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails) {
        String SQL_USER_UPDATE_PERSONAL = "INSERT INTO users (first_name,last_name,gender,dob) VALUES (?,?,?,?)";
        Object args = new Object[]{
                userUpdateProfilePersonalDetails.getFirst_name(),
                userUpdateProfilePersonalDetails.getLast_name(),
                userUpdateProfilePersonalDetails.getGender(),
                userUpdateProfilePersonalDetails.getDob()
        };
        jdbcTemplate.update(SQL_USER_UPDATE_PERSONAL, args);
    }
    @Override
    public void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation) {
        String SQL_USER_UPDATE_HEALTH = "INSERT INTO users (blood_type,genotype,known_allergies,pre_existing_conditions) VALUES (?,?,?,?)";
        Object args = new Object[]{
                userUpdateProfileHealthInformation.getBlood_type(),
                userUpdateProfileHealthInformation.getGenotype(),
                userUpdateProfileHealthInformation.getKnown_allergies(),
                userUpdateProfileHealthInformation.getPre_existing_conditions()
        };
        jdbcTemplate.update(SQL_USER_UPDATE_HEALTH, args);
    }
    @Override
    public void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation) {
        String SQL_USER_UPDATE_EMERGENCY = "INSERT INTO users (emergency_contact_name,emergency_contact_phone,emergency_contact_rel) VALUES (?,?,?)";
        Object args = new Object[]{
                userUpdateEmergencyInformation.getName(),
                userUpdateEmergencyInformation.getRelationship(),
                userUpdateEmergencyInformation.getPhone_number(),
        };
        jdbcTemplate.update(SQL_USER_UPDATE_EMERGENCY, args);
    }
    @Override
    public void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData) {
        String SQL_USER_UPDATE_LOCATION = "INSERT INTO users (nationality, state_of_origin) VALUES (?,?)";
        Object args = new Object[]{
                userUpdateLocationData.getCountry(),
                userUpdateLocationData.getState(),
        };
        jdbcTemplate.update(SQL_USER_UPDATE_LOCATION, args);
    }
}