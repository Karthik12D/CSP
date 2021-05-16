package com.cts.csp.domain;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResponse {

    private String result;

    private List<ErrorRecord> errorRecords;

}
