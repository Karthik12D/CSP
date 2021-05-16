package com.cts.csp.service.implementation;

import com.cts.csp.constants.CSPConstants;
import com.cts.csp.domain.ErrorRecord;
import com.cts.csp.domain.Record;
import com.cts.csp.domain.ValidationResponse;
import com.cts.csp.service.CSPService;
import com.cts.csp.util.CSPUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation class for CSPServiceInterface
 */
@Service
public class CSPServiceImplementation implements CSPService {

    @Override
    public ValidationResponse validateRecords(List<Record> records) {

        List<Record> duplicateRecords = retrieveDuplicateTransactionReferences(records);
        List<Record> endBalanceMismatchRecords = validateRecordsEndBalance(records);
        ValidationResponse validationResponse = getValidationResponse(duplicateRecords, endBalanceMismatchRecords);
        return validationResponse;
    }

    /**
     * Prepare validation response based on duplicate and mismatched balance results
     * @param duplicateRecords the duplicate reference results
     * @param endBalanceMismatchRecords the mismatched balance results
     * @return validation response
     */
    private ValidationResponse getValidationResponse(List<Record> duplicateRecords, List<Record> endBalanceMismatchRecords) {
        List<ErrorRecord> errorRecords = new ArrayList<>();
        ValidationResponse validationResponse = new ValidationResponse();
        if (!duplicateRecords.isEmpty()) {
            if (!endBalanceMismatchRecords.isEmpty()) {
                validationResponse.setResult(CSPConstants.RESULT_DUPLICATE_REFERENCE_AND_BALANCE_MISMATCH);

            } else {
                validationResponse.setResult(CSPConstants.RESULT_DUPLICATE_REFERENCE);
            }
        } else if (!endBalanceMismatchRecords.isEmpty()) {
            validationResponse.setResult(CSPConstants.RESULT_BALANCE_MISMATCH);

        } else {
            validationResponse.setResult(CSPConstants.RESULT_SUCCESSFUL);
        }
        errorRecords.addAll(duplicateRecords.stream().map(ErrorRecord::new).collect(Collectors.toList()));
        errorRecords.addAll(endBalanceMismatchRecords.stream().map(ErrorRecord::new).collect(Collectors.toList()));
        validationResponse.setErrorRecords(errorRecords);
        return validationResponse;
    }

    /**
     * Validate and filter duplicate references with records
     *
     * @param records the records which needs to be validated
     * @return dupicate references with records
     */
    private List<Record> retrieveDuplicateTransactionReferences(List<Record> records) {
        List<Record> duplicateRecords = new ArrayList<>();
        Map<String, List<Record>> duplicateTransactionReferences = records.stream()
                .collect(Collectors.groupingBy(record -> record.getTransactionReference()));

        duplicateTransactionReferences.forEach((k, v) -> {
            if (v.size() > 1) {
                duplicateRecords.addAll(v);
            }
        });

        return duplicateRecords;
    }

    /**
     * While making transaction validate the transaction (Start Balance - Mutation
     * Value = End Balance).
     *
     * @param records the records which needs to be validated
     * @return mismatched balance records
     */
    private List<Record> validateRecordsEndBalance(List<Record> records) {
        Function<Record, Record> functionToValidateTransactionAmount = record -> {
            char mutationSymbol = CSPUtils.getValidMutationSymbol(record.getMutation());
            BigDecimal mutationResult = mutationSymbol == '-' ? record.getStartBalance()
                    .subtract(new BigDecimal(record.getMutation().toString().substring(1))) : record.getStartBalance().add(record.getMutation());

            if (mutationResult.compareTo(record.getEndBalance()) != 0) {
                return record;
            } else {
                return null;
            }
        };

        return records.stream()
                .map(functionToValidateTransactionAmount)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
