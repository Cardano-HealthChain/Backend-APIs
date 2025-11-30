package com.cardano.healthchain.cardano.healthchain.user.dtos;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserCreateResponse {
    private String user_email;
    private String token;
}