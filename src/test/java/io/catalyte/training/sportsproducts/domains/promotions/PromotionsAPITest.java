package io.catalyte.training.sportsproducts.domains.promotions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.constants.Paths;
import io.catalyte.training.sportsproducts.constants.StringConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Test class for {@link PromotionalCodeController}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PromotionsAPITest {

    private MockMvc mockMvc;

    @Autowired
    private PromotionalCodeService promotionalCodeService;
    private String title;
    private String description;
    private PromotionalCodeType type;
    private BigDecimal rate;
    private PromotionalCode testCode;
    ObjectMapper mapper;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(
            new PromotionalCodeController(promotionalCodeService)).build();
        title = "SUMMER2015";
        description = "Our summer discount for the Q3 2015 campaign";
        type = PromotionalCodeType.FLAT;
        rate = BigDecimal.valueOf(10.00);
        testCode = new PromotionalCode(title, description, type, rate);
        mapper = new ObjectMapper();
    }
    @After
    public void tearDown() {
        promotionalCodeService.deletePromotionalCode(testCode);
    }
    public void savePromotionalCode(PromotionalCode code){
        PromotionalCodeDTO pojo = mapper.convertValue(code, PromotionalCodeDTO.class);
        promotionalCodeService.createPromotionalCode(pojo);
    }


    @Test
    public void createPromotionalCodeShouldReturn201StatusWhenGivenValidInput() throws Exception {
        PromotionalCode promotionalCode = new PromotionalCode(title, description, PromotionalCodeType.FLAT, rate);
        String expectedJson = "{\"title\": \"SUMMER2015\", \"description\": \"Our summer discount for the Q3 2015 campaign\", \"type\": \"FLAT\", \"rate\": 10.00}";

        mockMvc.perform(MockMvcRequestBuilders.post("/promotionalCodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testCode)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

    @Test
    public void verifyCodeReturns200StatusNoMatterWhatTest() throws Exception {
        String invalidTitle = "invalidTitle";
        PromotionalCode invalidCode = new PromotionalCode(invalidTitle, "newDescription", PromotionalCodeType.PERCENT, BigDecimal.valueOf(5));
        String expectedJson = String.format("{\"error\": \"%s\"}", StringConstants.INVALID_CODE);
        //test for a bad code
        mockMvc.perform(MockMvcRequestBuilders.get(Paths.PROMOCODE_PATH + "/" + invalidTitle))
            .andExpect(MockMvcResultMatchers.status().isOk());
        //test for a good code
        //save code first
        savePromotionalCode(testCode);
        mockMvc.perform(MockMvcRequestBuilders.get(Paths.PROMOCODE_PATH + "/" + testCode.getTitle()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


