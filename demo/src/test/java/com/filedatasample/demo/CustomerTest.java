package com.filedatasample.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filedatasample.demo.dto.CustomerDataDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import util.AbstractTestConfig;
import java.io.File;
import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CustomerTest extends AbstractTestConfig {

    private final Logger logger = LoggerFactory.getLogger(CustomerTest.class);
    private final String error_message = "Error occured while testing..!";


    MvcResult mvcResult;

    MockHttpServletResponse result;


    @Mock
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @After
    public void tearDown() {
        final File file = new File(System.getProperty("user.dir") + "//data//test.mv");
        file.delete();
    }


    @Test
    public void testSaveCustomerSuccess() {
        try {
            String inputJson = mapToJson(createCustomerDTO1());
            Mockito.verify(restTemplate, Mockito.times(1))
                    .exchange(inputJson,
                            Mockito.<HttpMethod>any(),
                            Mockito.<HttpEntity<?>>any(),
                            Mockito.<Class<?>>any());
        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(201, result.getStatus());
    }


    @Test
    public void testSaveCustomerFail() {
        final String url = "/fileData/fileDataSave";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO1());
            mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(400, result.getStatus());
    }


    @Test
    public void testUpdateCustomerSuccess() {
        final String url = "/fileData/fileDataUpdate/2";
        try {
            String inputJson = mapToJson(createCustomerDTO1());
            Mockito.verify(restTemplate, Mockito.times(1))
                    .exchange(inputJson,
                            Mockito.<HttpMethod>any(),
                            Mockito.<HttpEntity<?>>any(),
                            Mockito.<Class<?>>any());

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testUpdateCustomerFail() {
        final String url = "/fileData/fileDataUpdate/3";
        String inputJson;
        try {
            inputJson = mapToJson(createCustomerDTO());
            mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }

    @Test
    public void testFetchAllCustomer() {
        final String url = "/fileData/fileDataFetch?fileName=xyz.csv";

        try {
            String inputJson = mapToJson(createCustomerDTO1());
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testFetchAllCustomerNotFound() {
        final String url = "/fileData/fileDataFetch?fileName=abc.xml";

        try {
            mvcResult = mvc.perform(
                    MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_VALUE).header("accept-language", "en"))
                    .andReturn();

        } catch (final Exception e) {
            logger.error(error_message, e.getMessage());
        }
        result = mvcResult.getResponse();
        assertEquals(404, result.getStatus());
    }


    private CustomerDataDTO createCustomerDTO() {
        final CustomerDataDTO customerDataDTO = new CustomerDataDTO();
        customerDataDTO.setCustomerId("2");
        customerDataDTO.setAddress("Bangalore");
        customerDataDTO.setName("ABC");
        customerDataDTO.setSalary((double) 10000);
        customerDataDTO.setAge(26);
        return customerDataDTO;
    }

    private CustomerDataDTO createCustomerDTO1() {
        final CustomerDataDTO customerDataDTO = new CustomerDataDTO();
        customerDataDTO.setCustomerId("3");
        customerDataDTO.setAddress("Chennai");
        customerDataDTO.setName("XYZ");
        customerDataDTO.setSalary((double) 25000);
        customerDataDTO.setAge(30);
        return customerDataDTO;

    }
}
