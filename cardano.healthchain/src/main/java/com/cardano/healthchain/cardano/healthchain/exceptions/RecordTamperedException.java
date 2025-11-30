package com.cardano.healthchain.cardano.healthchain.exceptions;

public class RecordTamperedException extends RuntimeException{
    public RecordTamperedException(String userId, String clinic, String recordDate, String recordType) {
        String exceptionMessage = String.format(
                "Medical Record for user: %s, uploaded by: %s on %s for %s was tampered with",
                userId, clinic, recordDate, recordType);
    }
}
