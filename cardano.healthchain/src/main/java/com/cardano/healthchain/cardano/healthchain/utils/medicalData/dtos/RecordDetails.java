package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordDetails{
    private String record_id;
    private String record_hash;
}
