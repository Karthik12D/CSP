package com.cts.csp.controller;

import com.cts.csp.domain.Record;
import com.cts.csp.domain.ValidationResponse;
import com.cts.csp.service.CSPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Customer Statement Processor Controller will take input from request body as json.
 * It will validate the input and return response with validation result
 *
 */
@RestController
@RequestMapping("/csp")
public class CSPController {

    @Autowired
    private CSPService cspService;

    /**
     * Method to validate the customer statement records from request and will return validation result as json
     * @param records the customer statement records
     * @return response with validation result and http status code
     */
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ValidationResponse> validateRecords(@RequestBody List<Record> records) {
        ValidationResponse validationResponse = cspService.validateRecords(records);
        return new ResponseEntity<>(validationResponse, HttpStatus.OK);
    }
}
