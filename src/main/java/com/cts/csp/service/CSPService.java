package com.cts.csp.service;

import com.cts.csp.domain.Record;
import com.cts.csp.domain.ValidationResponse;

import java.util.List;

/**
 * Interface for CSP validation process
 */
public interface CSPService {

    /**
     * Interface to validate records which received from request and return response
     * @param records the records which needs to be validated
     * @return validation result with message and error records
     */
    ValidationResponse validateRecords(List<Record> records);
}
