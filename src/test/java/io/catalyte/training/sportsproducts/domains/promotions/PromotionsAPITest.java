package io.catalyte.training.sportsproducts.domains.promotions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionsAPITest {

    private static final String BASE_URL = "http://localhost:%d/promotions/codes";

    private ObjectMapper mapper;
    private String baseUrl;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        baseUrl = String.format(BASE_URL, port);
    }

    @Test
    public void testCreatePromotionalCodeWithFlatType() throws IOException {
        Map<String, Object> promotionalCodeMap = new HashMap<>();
        promotionalCodeMap.put("title", "SUMMER2015");
        promotionalCodeMap.put("description", "Our summer discount for the Q3 2015 campaign");
        promotionalCodeMap.put("type", "FLAT");
        promotionalCodeMap.put("rate", new BigDecimal("10.00"));

        String promotionalCodeJson = mapper.writeValueAsString(promotionalCodeMap);

        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(promotionalCodeJson);
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        Map<String, Object> responseMap = mapper.readValue(response.toString(), Map.class);

        assertEquals(promotionalCodeMap.get("title"), responseMap.get("title"));
        assertEquals(promotionalCodeMap.get("description"), responseMap.get("description"));
        assertEquals(promotionalCodeMap.get("type"), responseMap.get("type"));
        assertEquals(promotionalCodeMap.get("rate"), new BigDecimal(responseMap.get("rate").toString()));
    }

    @Test
    public void testCreatePromotionalCodeWithPercentType() throws IOException {
        Map<String, Object> promotionalCodeMap = new HashMap<>();
        promotionalCodeMap.put("title", "SPRINGSALE");
        promotionalCodeMap.put("description", "Get 20% off on all items in the store");
        promotionalCodeMap.put("type", "PERCENT");
        promotionalCodeMap.put("rate", new BigDecimal("20.00"));

        String promotionalCodeJson = mapper.writeValueAsString(promotionalCodeMap);

        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(promotionalCodeJson);
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        Map<String, Object> responseMap = mapper.readValue(response.toString(), Map.class);

        assertEquals(promotionalCodeMap.get("title"), responseMap.get("title"));
        assertEquals(promotionalCodeMap.get("description"), responseMap.get("description"));
        assertEquals(promotionalCodeMap.get("type"), responseMap.get("type"));
        assertEquals(promotionalCodeMap.get("rate"), new BigDecimal(responseMap.get("rate").toString()));
    }
}




