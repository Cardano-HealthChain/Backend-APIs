package com.cardano.healthchain.cardano.healthchain.controllerAdvice;

import com.cardano.healthchain.cardano.healthchain.exceptions.OtpException;
import com.cardano.healthchain.cardano.healthchain.exceptions.PendingUserException;
import com.cardano.healthchain.cardano.healthchain.exceptions.RecordTamperedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("DUPLICATE_RESOURCE","A record with that value already exists."));
    }
//    // Wrong column, bad data types, invalid SQL syntax
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_DATA","Your request contains invalid or incomplete fields."));
    }
//    // SQL syntax errors
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<?> handleBadSql(BadSqlGrammarException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SQL_ERROR","An unexpected database error occurred."));
    }
//    // Query returning nothing where expected
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleEmptyResult(EmptyResultDataAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND","The resource you requested does not exist."));
    }
//    // Catch-all for JdbcTemplate runtime SQL failures
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleGenericSql(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("DATABASE_ERROR","A database error occurred. Please try again later."));
    }
    // Fallback for any unhandled errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) throws Exception {
//        throw ex;
        System.out.println(ex.getMessage());
        System.out.println(ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR",ex.getMessage()));
    }
    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ErrorResponse> handleOtpException(OtpException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("OTP_ERROR","Invalid or expired OTP."));
    }

    @ExceptionHandler(PendingUserException.class)
    public ResponseEntity<ErrorResponse> handlePendingUserException(PendingUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("PENDING_USER","Your account is pending verification. Please complete the process."));
    }
    @ExceptionHandler(RecordTamperedException.class)
    public ResponseEntity<ErrorResponse> handleRecordTamperedException(RecordTamperedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("RECORD_TAMPERED","The data submitted is invalid or has been tampered with."));
    }
}