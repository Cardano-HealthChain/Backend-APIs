package com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos;

public enum NotificationMessages {
    DOCTOR_UPLOADED_RECORD("Your record has successfully been uploaded"),
    DOCTOR_REQUEST_USER_PERMISSION("You have a record permission request"),
    USER_GRANTED_PERMISSION_TO_CLINIC("Your permission request has been granted"),
    USER_REVOKED_PERMISSION_TO_CLINIC("Your permission request has been revoked");
    private final String message;
    NotificationMessages(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
