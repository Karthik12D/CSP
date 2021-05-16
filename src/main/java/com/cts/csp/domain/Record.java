package com.cts.csp.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Record {

    public Record(String transactionReference, String accountNumber, BigDecimal startBalance, BigDecimal mutation, String description, BigDecimal endBalance) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }

    private String transactionReference;

    private String accountNumber;

    private BigDecimal startBalance;

    private BigDecimal mutation;

    private String description;

    private BigDecimal endBalance;

}
