package com.cardano.healthchain.cardano.healthchain.controllerAdvice;

import com.cardano.healthchain.cardano.healthchain.exceptions.OtpException;
import com.cardano.healthchain.cardano.healthchain.exceptions.PendingUserException;
import com.cardano.healthchain.cardano.healthchain.exceptions.RecordTamperedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        com.cardano.healthchain.cardano.healthchain.controllerAdvice.ErrorResponse
                                .builder()
                                .code("A record with that value already exists.")
                                .message("DUPLICATE_RESOURCE")
                                .build()
                );
    }
    // Wrong column, bad data types, invalid SQL syntax
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(com.cardano.healthchain.cardano.healthchain.controllerAdvice.ErrorResponse
                                .builder()
                                .code("INVALID_DATA")
                                .message("Your request contains invalid or incomplete fields.")
                                .build()
                );
    }
    // SQL syntax errors
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<?> handleBadSql(BadSqlGrammarException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        com.cardano.healthchain.cardano.healthchain.controllerAdvice.ErrorResponse
                                .builder()
                                .message("INTERNAL_SQL_ERROR")
                                .code("An unexpected database error occurred.")
                                .build()
                );
    }
    // Query returning nothing where expected
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleEmptyResult(EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        com.cardano.healthchain.cardano.healthchain.controllerAdvice.ErrorResponse
                                .builder()
                                .code("The resource you requested does not exist.")
                                .message("NOT_FOUND")
                                .build()
                );
    }
    // Catch-all for JdbcTemplate runtime SQL failures
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleGenericSql(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        com.cardano.healthchain.cardano.healthchain.controllerAdvice.ErrorResponse
                                .builder()
                                .code("DATABASE_ERROR")
                                .message("A database error occurred. Please try again later.")
                                .build()
                );
    }
    // Fallback for any unhandled errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse
                                .builder()
                                .code("INTERNAL_ERROR")
                                .message("Something went wrong. Please try again.")
                                .build()
                );
    }
    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ErrorResponse> handleOtpException(OtpException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse
                                .builder()
                                .message("Invalid or expired OTP.")
                                .code("OTP_ERROR")
                                .build()
                );
    }

    @ExceptionHandler(PendingUserException.class)
    public ResponseEntity<ErrorResponse> handlePendingUserException(PendingUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse
                                .builder()
                                .code("PENDING_USER")
                                .message("Your account is pending verification. Please complete the process")
                                .build()
                );
    }

    @ExceptionHandler(RecordTamperedException.class)
    public ResponseEntity<ErrorResponse> handleRecordTamperedException(RecordTamperedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse
                                .builder()
                                .code("RECORD_TAMPERED")
                                .message("The data submitted is invalid or has been tampered with.")
                                .build()
                );
    }
}