package com.chamila.patientservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class PatientExceptionHandler {


    @ExceptionHandler(PatientRegistrationException.class)
    public ResponseEntity<String> handlePatientRegistrationException(PatientRegistrationException ex, Locale locale) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientServiceException.class)
    public ResponseEntity<String> handlePatientServiceException(PatientServiceException ex, Locale locale) {
         return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // Log the error to ELK stack or other logging systems
        // TODO: log the error
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

}
}

