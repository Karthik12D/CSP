package com.cts.csp.exception;

/**
 * Validation exception class
 */
public class CSPValidationException extends RuntimeException {

    /**
     * Constructor which sets exception message
     * @param message
     */
    public CSPValidationException(String message) {
        super(message);
    }
}
