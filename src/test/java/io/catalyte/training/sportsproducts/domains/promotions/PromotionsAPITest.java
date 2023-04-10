package io.catalyte.training.sportsproducts.domains.promotions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.promotions.model.PromotionalCode;
import com.example.promotions.model.PromotionalCode.Type;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PromotionsAPITest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/promotions/codes";
    }

    @Test
    public void testCreatePromotionalCodeWithFlatType() {
        PromotionalCode promotionalCode = new PromotionalCode();
        promotionalCode.setTitle("SUMMER2015");
        promotionalCode.setDescription("Our summer discount for the Q3 2015 campaign");
        promotionalCode.setType(PromotionalCodeType.FLAT);
        promotionalCode.setRate(10.00);

        given().contentType("application/json").body(promotionalCode).when().post(baseUrl).then()
                .statusCode(201).body("id", notNullValue()).body("title", is(promotionalCode.getTitle()))
                .body("description", is(promotionalCode.getDescription())).body("type", is("FLAT"))
                .body("rate", is(promotionalCode.getRate().floatValue()));
    }

    @Test
    public void testCreatePromotionalCodeWithPercentType() {
        PromotionalCode promotionalCode = new PromotionalCode();
        promotionalCode.setTitle("SPRINGSALE");
        promotionalCode.setDescription("Get 20% off on all items in the store");
        promotionalCode.setType(PromotionalCodeType.PERCENT);
        promotionalCode.setRate(20.00);

        given().contentType("application/json").body(promotionalCode).when().post(baseUrl).then()
                .statusCode(201).body("id", notNullValue()).body("title", is(promotionalCode.getTitle()))
                .body("description", is(promotionalCode.getDescription())).body("type", is("PERCENT"))
                .body("rate", is(promotionalCode.getRate().floatValue()));
    }
}

