package com.cts.csp.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"transactionReference", "accountNumber"})
public class ErrorRecord {

    private Record record;

    public ErrorRecord(Record record) {
        this.record = record;
    }

    public String getTransactionReference() {
        return this.record.getTransactionReference();
    }

    public String getAccountNumber() {
        return this.record.getAccountNumber();
    }

}
