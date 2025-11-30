package com.cardano.healthchain.cardano.healthchain.user.dtos;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserCreateResponse {
    private UUID userId;
    private String token;
}