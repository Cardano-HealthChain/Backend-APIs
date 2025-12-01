package com.cardano.healthchain.cardano.healthchain.utils;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class MedicalRecordHashGeneratorService {
    public String generateHashAndReturn(MedicalDataResponse medicalRecordForUser) throws JsonProcessingException, NoSuchAlgorithmException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        ObjectNode node = mapper.createObjectNode();
        node.put("record_id", medicalRecordForUser.getRecord_id());
        node.put("record_type", medicalRecordForUser.getRecord_type());
        node.put("record_data", medicalRecordForUser.getRecord_data());
        node.put("patientName", medicalRecordForUser.getPatientName());
        node.put("uploaded_by", medicalRecordForUser.getUploaded_by());
        node.put("created_at", medicalRecordForUser.getCreated_at().toString());

        String jsonString = mapper.writeValueAsString(node);        // create a hashing algorithm
        String hashValue = this.sha256(jsonString);
        medicalRecordForUser.setHash_local(hashValue);
        return hashValue;
    }
    public String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}