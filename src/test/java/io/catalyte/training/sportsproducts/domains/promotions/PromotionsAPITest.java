package io.catalyte.training.sportsproducts.domains.promotions;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.catalyte.training.sportsproducts.constants.Paths;
import io.catalyte.training.sportsproducts.constants.StringConstants;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
        Calendar cal = Calendar.getInstance();
        Date startDate = new Date();
        cal.setTime(startDate);
        testCode.setStartDate(startDate);
        cal.add(Calendar.DATE, 1);
        Date endDate = cal.getTime();
        testCode.setEndDate(endDate);
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
        String expectedJson = "{\"title\": \"SUMMER2016\", \"description\": \"Our summer discount for the Q3 2016 campaign\", \"type\": \"FLAT\", \"rate\": 10.00}";
        testCode.setTitle("SUMMER2016");
        testCode.setDescription("Our summer discount for the Q3 2016 campaign");
        mockMvc.perform(MockMvcRequestBuilders.post("/promotionalCodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testCode)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

    @Test
    public void getByTitleReturns200StatusTest() throws Exception {
        System.out.println("testCode = " + testCode.getEndDate());

        savePromotionalCode(testCode);
        mockMvc.perform(MockMvcRequestBuilders.get(Paths.PROMOCODE_PATH + "/" + testCode.getTitle()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void getByTitleReturnsCodeJsonTest() throws Exception {
        String expectedJson = mapper.writeValueAsString(testCode);
        savePromotionalCode(testCode);
        mockMvc.perform(MockMvcRequestBuilders.get(Paths.PROMOCODE_PATH + "/" + testCode.getTitle()))
            .andExpect(MockMvcResultMatchers.content()
                .json(expectedJson));
    }
    @Test
    public void getByTitleReturns404ErrorTest() throws Exception {
        testCode.setTitle("NewTitle");
        mockMvc.perform(MockMvcRequestBuilders.get(Paths.PROMOCODE_PATH + "/" + testCode.getTitle()))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


