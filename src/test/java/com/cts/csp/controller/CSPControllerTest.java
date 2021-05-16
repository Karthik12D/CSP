package com.cts.csp.controller;

import com.cts.csp.domain.ValidationResponse;
import com.cts.csp.service.CSPService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CSPController.class)
public class CSPControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CSPService cspService;

    /**
     * Method to test {@link CSPController#validateRecords(List)} with list of records
     * @throws Exception
     */
    @Test
    public void validateRecords() throws Exception {

        String recordsJson = "[\n" +
                "    {\n" +
                "        \"transactionReference\": \"1234\",\n" +
                "        \"accountNumber\": \"456\",\n" +
                "        \"startBalance\": 12,\n" +
                "        \"mutation\": -12,\n" +
                "        \"description\": \"cscsc\",\n" +
                "        \"endBalance\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"transactionReference\": \"12314\",\n" +
                "        \"accountNumber\": \"523\",\n" +
                "        \"startBalance\": 12,\n" +
                "        \"mutation\": 12,\n" +
                "        \"description\": \"cscsc\",\n" +
                "        \"endBalance\": 24\n" +
                "    },\n" +
                "    {\n" +
                "        \"transactionReference\": \"12324\",\n" +
                "        \"accountNumber\": \"523\",\n" +
                "        \"startBalance\": 12,\n" +
                "        \"mutation\": 12,\n" +
                "        \"description\": \"cscsc\",\n" +
                "        \"endBalance\": 24\n" +
                "    }\n" +
                "]";

        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setResult("SUCCESSFUL");
        validationResponse.setErrorRecords(new ArrayList<>());
        Mockito.when(cspService.validateRecords(Mockito.anyList())).thenReturn(validationResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/csp/validate").accept(MediaType.APPLICATION_JSON_VALUE).content(recordsJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertTrue(response.getContentAsString().contains("SUCCESSFUL"));


    }
}
