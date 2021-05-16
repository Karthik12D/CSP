package com.cts.csp.service;

import com.cts.csp.constants.CSPConstants;
import com.cts.csp.domain.Record;
import com.cts.csp.domain.ValidationResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * CSP service implementation test class
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CSPServiceImplTest {

    @Autowired
    private CSPService cspService;

    @Test
    public void testValidateRecords_WithDuplicateAndBalanceMismatch() {

        List<Record> records = new ArrayList<>();
        Record record1 = new Record("1234", "NL31CTS076892", new BigDecimal(2000), new BigDecimal(-200), "validation", new BigDecimal(1800));
        Record record2 = new Record("1233", "NL31CTS076892", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(1900));
        Record record3 = new Record("1233", "NL31CTS076892", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(1900));
        Record record4 = new Record("1223", "NL31CTS076892", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(1900));
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);
        ValidationResponse validationResponse = cspService.validateRecords(records);
        Assert.assertEquals(validationResponse.getResult(), CSPConstants.RESULT_DUPLICATE_REFERENCE_AND_BALANCE_MISMATCH);
        Assert.assertEquals(validationResponse.getErrorRecords().size(), 5);
    }

    @Test
    public void testValidateRecords_WithBalanceMismatch() {

        List<Record> records = new ArrayList<>();
        Record record1 = new Record("1234", "NL31CTS076892", new BigDecimal(2000), new BigDecimal(-200), "validation", new BigDecimal(1800));
        Record record2 = new Record("1243", "NL31CTS076897", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record3 = new Record("1233", "NL31CTS076891", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record4 = new Record("1223", "NL31CTS076895", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(250));
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);
        ValidationResponse validationResponse = cspService.validateRecords(records);
        Assert.assertEquals(validationResponse.getResult(), CSPConstants.RESULT_BALANCE_MISMATCH);
        Assert.assertEquals(validationResponse.getErrorRecords().size(), 1);
    }

    @Test
    public void testValidateRecords_WithDuplicateReference() {

        List<Record> records = new ArrayList<>();
        Record record1 = new Record("1234", "NL31CTS076892", new BigDecimal(2000), new BigDecimal(-200), "validation", new BigDecimal(1800));
        Record record2 = new Record("1233", "NL31CTS076897", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record3 = new Record("1233", "NL31CTS076897", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record4 = new Record("1223", "NL31CTS076895", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);
        ValidationResponse validationResponse = cspService.validateRecords(records);
        Assert.assertEquals(validationResponse.getResult(), CSPConstants.RESULT_DUPLICATE_REFERENCE);
        Assert.assertEquals(validationResponse.getErrorRecords().size(), 2);
    }

    @Test
    public void testValidateRecords_WithSuccessfulResult() {

        List<Record> records = new ArrayList<>();
        Record record1 = new Record("1234", "NL31CTS076892", new BigDecimal(2000), new BigDecimal(-200), "validation", new BigDecimal(1800));
        Record record2 = new Record("1235", "NL31CTS076896", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record3 = new Record("1233", "NL31CTS076897", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        Record record4 = new Record("1223", "NL31CTS076895", new BigDecimal(2500), new BigDecimal(200), "validation", new BigDecimal(2700));
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);
        ValidationResponse validationResponse = cspService.validateRecords(records);
        Assert.assertEquals(validationResponse.getResult(), CSPConstants.RESULT_SUCCESSFUL);
        Assert.assertEquals(validationResponse.getErrorRecords().size(), 0);
    }
}
