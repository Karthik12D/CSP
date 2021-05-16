package com.cts.csp.exception;

import com.cts.csp.constants.CSPConstants;
import com.cts.csp.domain.ValidationResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

/**
 * Exception handler for CSP validation
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CSPExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method to handle validation failure exceptions which returns 500 server error
     * @return
     */
    @ExceptionHandler(value = CSPValidationException.class)
    public ResponseEntity<ValidationResponse> handleValidationException() {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setResult(CSPConstants.PRE_CONDITION_FAILED);
        validationResponse.setErrorRecords(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(validationResponse);
    }

    /**
     * Method to handle validation failure exceptions which returns 500 server error
     * @return response entity with internal server error status code
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ValidationResponse> handleException() {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setResult(CSPConstants.INTERNAL_SERVER_ERROR);
        validationResponse.setErrorRecords(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(validationResponse);
    }

    /**
     * Method to handle validation failure exceptions which returns 400 bad request
     * @return response entity with internal server error status code
     */
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setResult(CSPConstants.BAD_REQUEST);
        validationResponse.setErrorRecords(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResponse);
    }
}
