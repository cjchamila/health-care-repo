package com.chamila.appointmentservice.exception;

public class ResourceNotFoundException extends RuntimeException {
       public ResourceNotFoundException(String message) {
        super(message);
    }
}
