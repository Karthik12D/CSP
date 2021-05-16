package com.cts.csp;

import com.cts.csp.constants.CSPConstants;
import com.cts.csp.domain.Record;
import com.cts.csp.domain.ValidationResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Integration test class
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CSPApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CSPIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testValidateRecords() {

        List<Record> records = new ArrayList<>();
        Record record1 = new Record("1234", "NL31CTS076892", new BigDecimal(2000), new BigDecimal(-200), "validation", new BigDecimal(1800));
        records.add(record1);
        HttpEntity<List<Record>> entity = new HttpEntity<>(records, new HttpHeaders());
        ResponseEntity<ValidationResponse> responseEntity = restTemplate.exchange(createURLWithPort("/csp/validate"), HttpMethod.POST, entity , ValidationResponse.class);
        Assert.assertEquals(responseEntity.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
        Assert.assertEquals(responseEntity.getBody().getResult(), CSPConstants.RESULT_SUCCESSFUL);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
