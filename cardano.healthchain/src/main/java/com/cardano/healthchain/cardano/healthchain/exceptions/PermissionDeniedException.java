package com.cardano.healthchain.cardano.healthchain.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException() {
        super("Permission type required is not granted");
    }
}